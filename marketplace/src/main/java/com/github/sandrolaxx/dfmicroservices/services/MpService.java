package com.github.sandrolaxx.dfmicroservices.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonNumber;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.sandrolaxx.dfmicroservices.dto.OrderDto;
import com.github.sandrolaxx.dfmicroservices.dto.ProductOrderDto;
import com.github.sandrolaxx.dfmicroservices.entities.Address;
import com.github.sandrolaxx.dfmicroservices.entities.Cart;
import com.github.sandrolaxx.dfmicroservices.entities.Order;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.ProductCart;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumErrorCode;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumOrderStatus;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPaymentType;
import com.github.sandrolaxx.dfmicroservices.utils.EncryptUtil;
import com.github.sandrolaxx.dfmicroservices.utils.FrostException;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class MpService {

    public Uni<Response> addProductToCart(Uni<Cart> cart, Integer idProduct,
            Integer quantity) throws FrostException {

        return Product.<Product>findById(idProduct)
                .onItem()
                .ifNotNull()
                .call(p -> {
                    return cart
                            .onItem()
                            .ifNotNull()
                            .call(c -> {

                                boolean existsProduct = c.getProductCartList().stream()
                                        .filter(pc -> pc != null
                                                && pc.getProduct() != null
                                                && pc.getProduct().getId() == p.getId())
                                        .findFirst()
                                        .isPresent();

                                if (existsProduct) {
                                    throw new FrostException(EnumErrorCode.PRODUTO_JA_EXISTENTE_NO_CARRINHO);
                                }

                                var productCart = new ProductCart();
                                productCart.setCart(c);
                                productCart.setProduct(p);
                                productCart.setActive(true);
                                productCart.setQuantity(quantity);

                                return Panache.withTransaction(productCart::persist);

                            })
                            .onItem().ifNull().failWith(new FrostException(EnumErrorCode.CARRINHO_NAO_ENCONTRADO));
                })
                .onItem().ifNull().failWith(new FrostException(EnumErrorCode.PRODUTO_NAO_ENCONTRADO))
                .onItem().ifNotNull().transform(p -> Response.ok().status(Status.CREATED).build());

    }

    public Uni<Response> addQuantityToProductCart(Integer idProductCart, Integer quantity) {

        return ProductCart.<ProductCart>findById(idProductCart)
                .onItem()
                .ifNotNull()
                .call(pc -> {
                    pc.setQuantity(pc.getQuantity() + quantity);

                    return Panache.withTransaction(pc::persist);
                })
                .onItem().ifNotNull().transform(c -> Response.ok().status(Status.NO_CONTENT).build())
                .onItem().ifNull().failWith(new FrostException(EnumErrorCode.CARRINHO_NAO_ENCONTRADO));

    }

    public Uni<Response> removeQuantityToProductCart(Integer idProductCart, Integer quantity) {

        return ProductCart.<ProductCart>findById(idProductCart)
                .onItem()
                .ifNotNull()
                .call(pc -> {

                    if (quantity == null
                            || pc.getQuantity() - quantity <= 0) {
                        return Panache.withTransaction(pc::delete);
                    }

                    pc.setQuantity(pc.getQuantity() - quantity);

                    return Panache.withTransaction(pc::persist);
                })
                .onItem().ifNotNull().transform(c -> Response.ok().status(Status.NO_CONTENT).build())
                .onItem().ifNull().failWith(new FrostException(EnumErrorCode.CARRINHO_NAO_ENCONTRADO));

    }

    public Uni<Response> cartToOrder(Uni<Cart> cart, EnumPaymentType payType) {

        Double deliveryValue = 12.0;

        return cart
                .onItem()
                .ifNotNull()
                .call(c -> {
                    Order order = new Order();
                    Address address = c.getUser().getAddress().stream()
                            .filter(adr -> adr.isMain())
                            .findFirst()
                            .get();

                    c.getProductCartList().stream().map(pc -> {
                        pc.setCart(null);
                        pc.setOrder(order);
                        return pc;
                    })
                    .collect(Collectors.toList());

                    order.setAddress(address);
                    order.setDeliveryValue(deliveryValue);

                    order.setOrderStatus(EnumOrderStatus.AWAITING_PAYMENT);
                    order.setPaymentType(payType);
                    order.setTotal(this.sumTotal(c.getProductCartList()));
                    order.setUser(c.getUser());
                    order.setProductOrderList(c.getProductCartList());

                    c.setProductCartList(null);

                    return Panache.withTransaction(order::persist);
                })
                .onItem().ifNotNull().transform(c -> Response.ok().status(Status.CREATED).build())
                .onItem().ifNull().failWith(new FrostException(EnumErrorCode.CARRINHO_NAO_ENCONTRADO));

    }

    public Uni<Response> listOrders(Integer idUser, EnumOrderStatus orderStatus) {

        Uni<List<Order>> uniListOrder;

        if (idUser == null) {
            uniListOrder = Order.findAllOrdersByStatus(orderStatus);
        } else {
            uniListOrder = Order.findAllOrdersByidUser(idUser, orderStatus);
        }

        return uniListOrder.onItem()
                .ifNotNull().transform(orderList -> {

                    List<OrderDto> listOrderDto = new ArrayList<>();

                    orderList.stream().forEach(order -> {
                        OrderDto orderDto = new OrderDto();
                        List<ProductOrderDto> listProductOrderDto = new ArrayList<>();
                        Address adr = order.getAddress();

                        order.getProductOrderList().stream().forEach(productOrder -> {
                            ProductOrderDto productDto = new ProductOrderDto();

                            productDto.setProduct(productOrder.getProduct());
                            productDto.setQuantity(productOrder.getQuantity());

                            listProductOrderDto.add(productDto);
                        });

                        orderDto.setOrderId(order.getId());
                        orderDto.setTotal(order.getTotal());
                        orderDto.setDeliveryValue(order.getDeliveryValue());
                        orderDto.setClientName(EncryptUtil.textDecrypt(order.getUser().getName(), order.getUser().getSecret()));

                        orderDto.setLatitude(Double.valueOf(EncryptUtil.textDecrypt(adr.getLatitude(), adr.getSecret())));
                        orderDto.setLongitude(Double.valueOf(EncryptUtil.textDecrypt(adr.getLongitude(), adr.getSecret())));
                        orderDto.setDistrict(EncryptUtil.textDecrypt(adr.getDistrict(), adr.getSecret()));
                        orderDto.setStreet(EncryptUtil.textDecrypt(adr.getStreet(), adr.getSecret()));
                        orderDto.setNumber(adr.getNumber());
                        orderDto.setNumberAp(adr.getNumberAp());

                        orderDto.setProductsOrder(listProductOrderDto);
                        orderDto.setOrderStatus(order.getOrderStatus());
                        orderDto.setPayType(order.getPaymentType());
                        orderDto.setOrderDate(order.createdAt.getTime());

                        listOrderDto.add(orderDto);

                    });
                    return Response.ok(listOrderDto).build();

                })
                .onItem().ifNull().failWith(new FrostException(EnumErrorCode.NENHUM_PEDIDO_ENCONTRADO));

    }

    public Uni<Response> updateOrder(Integer orderId, EnumOrderStatus newStatus) {
        return Order.<Order>findById(orderId)
                .onItem()
                .ifNotNull()
                .call(order -> {
                    order.setOrderStatus(newStatus);

                    return Panache.withTransaction(order::persist);
                })
                .onItem().ifNotNull().transform(c -> Response.ok().status(Status.NO_CONTENT).build())
                .onItem().ifNull().failWith(new FrostException(EnumErrorCode.CARRINHO_NAO_ENCONTRADO));
    }

    public Double sumTotal(List<ProductCart> productCartList) {
        return productCartList.stream().mapToDouble(pc -> {
            return (pc.getProduct().getPrice() - pc.getProduct().getDiscount())
                * pc.getQuantity();
        }).sum();
    }

    public Uni<Cart> resolveIdCart(SecurityIdentity identity) {
        return Cart.findByUserId(this.resolveUserId(identity));
    }

    public Integer resolveUserId(SecurityIdentity identity) {
        var tokenInfo = (OidcJwtCallerPrincipal) identity.getPrincipal();
        var userId = (JsonNumber) tokenInfo.getClaim("userId");

        return userId.intValue();
    }

}

package com.github.sandrolaxx.dfmicroservices.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.sandrolaxx.dfmicroservices.entities.Address;
import com.github.sandrolaxx.dfmicroservices.entities.Cart;
import com.github.sandrolaxx.dfmicroservices.entities.Order;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.ProductCart;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumErrorCode;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumOrderStatus;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPaymentType;
import com.github.sandrolaxx.dfmicroservices.utils.FrostException;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class MpService {

    Double total = 0.0;

    public Uni<Response> addProductToCart(String idCart, 
        Integer idProduct, Integer quantity) throws FrostException {

        return Product.<Product>findById(idProduct)
                    .onItem()
                    .ifNotNull()
                    .call(p -> {
                        return Cart.<Cart>findById(idCart)
                            .onItem()
                            .ifNotNull()
                            .call(c -> {

                                boolean existsProduct = c.getProductCartList().stream()
                                                                              .filter(pc -> pc != null && pc.getProduct() != null
                                                                                        && pc.getProduct().getId() == p.getId())
                                                                              .findFirst()
                                                                              .isPresent();
                                if(existsProduct) {
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
                    
    public Uni<Cart> listAllCart(String idCart) {
        return Cart.findById(idCart);
    }

    public Uni<Response> addQuantityToProductCart(Integer idProductCart, Integer quantity) {

        return ProductCart.<ProductCart>findById(idProductCart)
                    .onItem()
                    .ifNotNull()
                    .call(pc -> {
                        System.out.println(pc.getQuantity());

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
                            || pc.getQuantity() - quantity <= 0 ) {
                            return Panache.withTransaction(pc::delete);
                        }
                        
                        pc.setQuantity(pc.getQuantity() - quantity);

                        return Panache.withTransaction(pc::persist);       
                    })
                    .onItem().ifNotNull().transform(c -> Response.ok().status(Status.NO_CONTENT).build())
                    .onItem().ifNull().failWith(new FrostException(EnumErrorCode.CARRINHO_NAO_ENCONTRADO)); 

    }

    public Uni<Response> cartToOrder(String idCart, EnumPaymentType payType) {
        
        return Cart.<Cart>findById(idCart)
                    .onItem()
                    .ifNotNull()
                    .call(c -> {
                        Order order = new Order();
                        Address address = c.getUser().getAddress().stream()
                                                                  .filter(adr -> adr.isMain())
                                                                  .findFirst()
                                                                  .get(); 
                                                                  
                        c.getProductCartList().stream()
                                .forEach(p -> {
                                    this.sumTotal(p);
                                    p.setCart(null);
                                    p.setOrder(order);
                                });

                        order.setAddressDescription(this.getAddressDescription(address));
                        order.setLititude(address.getLatitude());
                        order.setLongitude(address.getLongitude());
                        order.setDeliveryValue(12.0);

                        order.setOrderStatus(EnumOrderStatus.AWAITING_PAYMENT);
                        order.setPaymentType(payType);
                        order.setTotal(total);
                        order.setUser(c.getUser());
                        order.setProductOrderList(c.getProductCartList());
                        
                        c.setProductCartList(null);

                        return Panache.withTransaction(order::persist);       
                    })
                    .onItem().ifNotNull().transform(c -> Response.ok().status(Status.CREATED).build())
                    .onItem().ifNull().failWith(new FrostException(EnumErrorCode.CARRINHO_NAO_ENCONTRADO));

    }

    public Uni<Response> listOrders(Integer idUser, EnumOrderStatus status) {
        return null;//retornar List<Uni<Order>> descriptografado
    }

    public Double sumTotal(ProductCart productCart) {
        System.out.println("----------------------------------------------------");
        System.out.println(productCart.getProduct().getPrice());
        System.out.println(productCart.getProduct().getDiscount());
        System.out.println(productCart.getQuantity());
        System.out.println(total);
        System.out.println("----------------------------------------------------");
        
        total += (productCart.getProduct().getPrice() - productCart.getProduct().getDiscount()) * productCart.getQuantity();
        
        System.out.println(total);
        return total;
    }

    public String getAddressDescription(Address adr) {
        return adr.getDistrict().concat(",").concat(adr.getStreet()).concat(",")
                                    .concat(adr.getNumber().toString())
                                    .concat(adr.getNumberAp() == null ? "" : "-".concat(adr.getNumberAp().toString()));  
    }

}


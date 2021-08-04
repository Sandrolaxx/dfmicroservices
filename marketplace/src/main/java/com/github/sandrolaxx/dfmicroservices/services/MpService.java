package com.github.sandrolaxx.dfmicroservices.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.sandrolaxx.dfmicroservices.entities.Cart;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.ProductCart;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumErrorCode;
import com.github.sandrolaxx.dfmicroservices.utils.FrostException;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class MpService {
    
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

}


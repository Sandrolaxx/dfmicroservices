package com.github.sandrolaxx.dfmicroservices.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.sandrolaxx.dfmicroservices.dto.ProductDto;
import com.github.sandrolaxx.dfmicroservices.entities.Cart;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.ProductCart;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumErrorCode;
import com.github.sandrolaxx.dfmicroservices.utils.FrostException;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class MpService {
    
    public Uni<Response> addProductToCart(Integer idCart, 
        Integer idProduct, Integer quantity) throws FrostException {

        return Product.<Product>findById(idProduct)
                    .onItem()
                    .ifNotNull()
                    .call(p -> {
                        return Cart.<Cart>findById(idCart)
                            .onItem()
                            .ifNotNull()
                            .call(c -> {
                                var productCart = new ProductCart();
                                productCart.setCart(c);
                                productCart.setProduct(p);
                                productCart.setQuantity(quantity);
                    
                                return Panache.withTransaction(productCart::persist);       
                            })
                            .onItem().ifNull().failWith(new FrostException(EnumErrorCode.ERRO_AO_CADASTRAR_PRODUTO)); 
                        })
                        .onItem().ifNull().failWith(new FrostException(EnumErrorCode.PRODUTO_NAO_ENCONTRADO))
                        .onItem().ifNotNull().transform(p -> Response.ok().status(Status.CREATED).build());
        
    }

    public Multi<ProductDto> listProducts() {

        Multi<Object> productList = Product.listAll()
                                           .onItem()
                                           .transformToMulti(m -> Multi.createFrom().iterable(m));

        return productList.onItem()
                          .transform(m -> new ProductDto((Product) m));

    }

}

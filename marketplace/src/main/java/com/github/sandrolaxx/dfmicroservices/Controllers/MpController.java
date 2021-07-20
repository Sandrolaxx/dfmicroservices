package com.github.sandrolaxx.dfmicroservices.Controllers;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.sandrolaxx.dfmicroservices.dto.ProductDto;
import com.github.sandrolaxx.dfmicroservices.entities.Cart;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.ProductCart;
import com.github.sandrolaxx.dfmicroservices.services.MpService;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/dona-frost/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MpController {

    @Inject
    MpService service;

    @GET
    @Path("/products")
    public Multi<ProductDto> findAllProducts() {

        Multi<Object> productList = Product.listAll()
                                           .onItem()
                                           .transformToMulti(m -> Multi.createFrom().iterable(m));

        return productList.onItem()
                          .transform(m -> new ProductDto((Product) m));
    }

    @POST
    @Path("/cart")
    public Uni<Product> addProductOnCart(@HeaderParam("idProduct") Integer idProduct, 
        @HeaderParam("idCart") Integer idCart, @HeaderParam("quantity") Integer quantity) {
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
                
                            return Panache.withTransaction(productCart::persist)
                                .onItem().transform(newcart -> Response.ok(newcart).build());       
                        });      
                });
    }


}
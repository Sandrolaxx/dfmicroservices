package com.github.sandrolaxx.dfmicroservices.Controllers;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.sandrolaxx.dfmicroservices.dto.ListProductCartUpdate;
import com.github.sandrolaxx.dfmicroservices.dto.ProductDto;
import com.github.sandrolaxx.dfmicroservices.entities.Cart;
import com.github.sandrolaxx.dfmicroservices.entities.ProductCart;
import com.github.sandrolaxx.dfmicroservices.services.MpService;

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
        return service.listProducts();
    }

    @GET
    @Path("/cart")
    public Uni<List<ProductCart>> findAllCart(@HeaderParam("idCart") Integer idCart) {
        Uni<Cart> cart = service.listAllCart(idCart);

        return cart.onItem().transform(c -> c.getProductCartList());
    }

    @POST
    @Path("/cart")
    public Uni<Response> addProductOnCart(@HeaderParam("idProduct") Integer idProduct, 
        @HeaderParam("idCart") Integer idCart, @HeaderParam("quantity") Integer quantity) {
        return service.addProductToCart(idCart, idProduct, quantity);
    }

    @PUT
    @Path("/cart")
    public Uni<Response> updateCart(@HeaderParam("idCart") Integer idCart, ListProductCartUpdate listToUpdate) {
        return service.updateCart(idCart, listToUpdate);
    }

}
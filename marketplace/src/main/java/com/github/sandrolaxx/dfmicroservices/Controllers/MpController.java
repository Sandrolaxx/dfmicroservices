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

    @POST
    @Path("/cart")
    public Uni<Response> addProductOnCart(@HeaderParam("idProduct") Integer idProduct, 
        @HeaderParam("idCart") Integer idCart, @HeaderParam("quantity") Integer quantity) {
        return service.addProductToCart(idCart, idProduct, quantity);
    }


}
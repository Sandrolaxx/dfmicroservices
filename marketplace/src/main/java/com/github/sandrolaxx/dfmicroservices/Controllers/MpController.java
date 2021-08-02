package com.github.sandrolaxx.dfmicroservices.Controllers;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.sandrolaxx.dfmicroservices.dto.ListProductCart;
import com.github.sandrolaxx.dfmicroservices.services.MpService;

@Path("/dona-frost/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MpController {

    @Inject
    MpService service;

    @GET
    @Path("/cart")
    public Response listProductsByIdCart(@HeaderParam("idCart") Integer idCart) {
        return Response.ok().build();
    }

    @POST
    @Path("/cart")
    public Response addProductsOnCart(@HeaderParam("idCart") Integer idCart, ListProductCart listProducts) {
        return service.addProductsToCart(idCart, listProducts);
    }

    @PUT
    @Path("/cart")
    public Response updateCart(@HeaderParam("idCart") Integer idCart, ListProductCart listProducts) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/cart")
    public Response deleteProducts(@HeaderParam("idCart") Integer idCart, ListProductCart listProducts) {
        return Response.ok().build();
    }

}
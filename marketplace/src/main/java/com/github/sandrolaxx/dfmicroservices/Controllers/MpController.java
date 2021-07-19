package com.github.sandrolaxx.dfmicroservices.Controllers;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.ProductCart;
import com.github.sandrolaxx.dfmicroservices.services.MpService;

import io.smallrye.mutiny.Uni;

@Path("/dona-frost/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MpController {

    @Inject
    MpService service;

    @GET
    @Path("/products")
    public Uni<List<Product>> findAllProducts() {

        Uni<List<Product>> productList = Product.listAll();

        return productList;
    }

    @POST
    public List<ProductCart> addProductOnCart(@HeaderParam("idProduct") Integer idProduct, 
        @HeaderParam("idCart") Integer idCart, @HeaderParam("quantity") Integer quantity) {
            return service.addProductToCart(idCart, idProduct, quantity);
    }

}
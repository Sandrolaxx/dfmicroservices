package com.github.sandrolaxx.dfmicroservices.Controllers;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.sandrolaxx.dfmicroservices.dto.ProductDto;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.repositories.ProductRepository;

import io.smallrye.mutiny.Multi;

@Path("/dona-frost/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MpController {

    @Inject
    ProductRepository repository;

    @GET
    @Path("/products")
    public Multi<ProductDto> findProducts() {
        Multi<Product> productList = Multi.createFrom().iterable(repository.list());

        return productList.map(p -> new ProductDto(p));
    }

}
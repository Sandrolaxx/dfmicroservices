package com.github.sandrolaxx.dfmicroservices.Controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/dona-frost/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MpController {

    // @Path("/products")
    // public Multi<ProductDto> findProducts() {
    //     Multi<Product> productList = Product.streamAll();

    //     return productList.map(p -> new ProductDto(p));
    // }

}
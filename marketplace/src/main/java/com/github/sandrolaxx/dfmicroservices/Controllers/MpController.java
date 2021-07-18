package com.github.sandrolaxx.dfmicroservices.Controllers;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.sandrolaxx.dfmicroservices.dto.ProductDto;
import com.github.sandrolaxx.dfmicroservices.entities.Product;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

@Path("/dona-frost/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MpController {

    @Inject
    PgPool client;

    @GET
    @Path("/products")
    public Multi<ProductDto> findAllProducts() {

        Multi<Product> productList = client.query("SELECT * FROM DF_PRODUCT").execute()
                                           .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                                           .onItem().transform(Product::from);

        return productList.map(p -> new ProductDto(p));
    }

    @GET
    @Path("/products/{id}")
    public Uni<ProductDto> findById(@PathParam("id") Long id) {
        Uni<Product> productEntity =  client.preparedQuery("SELECT * FROM DF_PRODUCT WHERE id = $1").execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? Product.from(iterator.next()) : null);

        return productEntity.map(product -> new ProductDto(product));     
    }

    @POST
    public void addProductOnCart(@PathParam("idProduct") Long idProduct, 
        @PathParam("idUser") Long idUser) {

            // Uni<Cart> productEntity =  client.preparedQuery("SELECT * FROM DF_CART WHERE id = $1").execute(Tuple.of(idUser))
            //         .onItem().transform(RowSet::iterator)
            //         .onItem().transform(iterator -> iterator.hasNext() ? Product.from(iterator.next()) : null);
    }

}
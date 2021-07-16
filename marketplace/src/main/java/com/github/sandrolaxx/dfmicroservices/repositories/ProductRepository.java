package com.github.sandrolaxx.dfmicroservices.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPlateCategory;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPlateSize;

import io.vertx.core.Future;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

@ApplicationScoped
public class ProductRepository {
    
    @Inject
    PgPool client;

    public void persist(Product product) {
        
        client.preparedQuery("INSERT INTO DF_PRODUCT "                       +
                             "(ID, NAME, DESCRIPTION, IMAGE_URI, CATEGORY, " +
                             "PLATE_SIZE, PRICE, DISCOUNT, ACTIVE) "         +
                             " VALUES "                                      +
                             "($1, $2, $3, $4, $5, $6, $7, $8, $9)")
              .execute(Tuple.tuple()
                            .addInteger(product.getId())
                            .addString(product.getName())
                            .addString(product.getDescription())
                            .addString(product.getImageUri())
                            .addValue(product.getCategory())
                            .addValue(product.getPlateSize())
                            .addDouble(product.getPrice())
                            .addDouble(product.getDiscount())
                            .addBoolean(product.isActive())
              );

    }

    public void update(Product product) {

        this.findById(product.getId()).onComplete(tr -> {

            if (tr.succeeded()) {

                for (Row row : tr.result()) {

                    client.preparedQuery("UPDATE DF_PRODUCT SET "                        + 
                                         "NAME = $1, DESCRIPTION = $2, IMAGE_URI = $3, " +
                                         "CATEGORY = $4, PLATE_SIZE = $5, PRICE = $6,  " +
                                         "DISCOUNT = $7, ACTIVE = $8"                    + 
                                            " WHERE "                                    +
                                        "ID = $9")
                          .execute(Tuple.tuple()
                                        .addString(product.getName().isEmpty() ? 
                                            row.getString("name") : product.getName())
                                        .addString(product.getDescription().isEmpty() ? 
                                            row.getString("description") : product.getDescription())
                                        .addString(product.getImageUri().isEmpty() ? 
                                            row.getString("image_uri") : product.getImageUri())
                                        .addValue(product.getCategory() == null ? 
                                            row.getValue("category") : product.getCategory())
                                        .addValue(product.getPlateSize() == null ? 
                                            row.getValue("plate_size") : product.getPlateSize())
                                        .addDouble(product.getPrice() == null ? 
                                            row.getDouble("price") : product.getPrice())
                                        .addDouble(product.getDiscount() == null ? 
                                            row.getDouble("discount") : product.getDiscount())
                                        .addBoolean(product.isActive() == row.getBoolean("active") ?
                                            row.getBoolean("active") : product.isActive())
                                        .addInteger(product.getId())
                          );
        
                }

            } else {
                System.out.println("Failure: " + tr.cause().getMessage());
            }               

        });

    }

    public void delete(Product product) {

        client.preparedQuery("DELETE FROM DF_PRODUCT" + 
                             " WHERE " +
                             "ID = $1")
              .execute(Tuple.of(product.getId()));

    }

    public Future<RowSet<Row>> findById(Integer idProduct) {
        
        return client.preparedQuery("SELECT * FROM DF_PRODUCT" + 
                                    " WHERE " +
                                    "ID = $1")
                     .execute(Tuple.of(idProduct));

    }

    public List<Product> list() {

        List<Product> productList = new ArrayList<>();

        client.preparedQuery("SELECT * FROM DF_PRODUCT" + 
                                    " WHERE " +
                                    "ACTIVE IS TRUE")
              .execute().onSuccess(rowSet -> {
                
                    for (Row row : rowSet) {
                        productList.add(this.from(row));
                    }

              }).toCompletionStage();

            return productList;

    }

    public Product from(Row row) {
        
        var product = new Product();

        product.setName(row.getString("name"));
        product.setDescription(row.getString("description"));
        product.setImageUri(row.getString("image_uri"));
        product.setCategory(EnumPlateCategory.fromString(row.getString("category")));
        product.setPlateSize(EnumPlateSize.fromString(row.getString("plate_size")));
        product.setPrice(row.getDouble("price"));
        product.setDiscount(row.getDouble("discount"));
        product.setActive(row.getBoolean("active"));

        return product;

    }

}

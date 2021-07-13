package com.github.sandrolaxx.dfmicroservices.repositories;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.sandrolaxx.dfmicroservices.entities.Address;
import com.github.sandrolaxx.dfmicroservices.entities.User;

import io.vertx.core.Future;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

@ApplicationScoped
public class UserRepository {

    @Inject
    PgPool client;

    public void persist(User user) {

        client.preparedQuery("INSERT INTO DF_USER " +
                             "(ID, DOCUMENT, EMAIL, NAME, PASSWORD, PHONE, " +
                             "SECRET, ACCEPT_TERMS, ACTIVE)" +
                                " VALUES " +
                             "($1, $2, $3, $4, $5, $6, $7, $8, $9)")
              .execute(Tuple.tuple()
                            .addInteger(user.getId())
                            .addString(user.getDocument())
                            .addString(user.getEmail())
                            .addString(user.getName())
                            .addString(user.getPassword())
                            .addString(user.getPhone())
                            .addString(user.getSecret())
                            .addBoolean(user.isAcceptTerms())
                            .addBoolean(user.isActive())
               ).onComplete(tr -> {

                    Address adrress = user.getAddress().get(0);
                    
                    client.preparedQuery("INSERT INTO DF_USER_ADDRESS "+
                                         "(ID, ID_USER, STATE, CITY, DISTRICT, STREET, NUMBER, " +
                                         "NUMBER_AP, LATITUDE, LONGITUDE, SECRET, ACTIVE, MAIN)" +
                                         " VALUES " +
                                         "($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13)")
                          .execute(Tuple.tuple()
                                        .addInteger(adrress.getId())
                                        .addInteger(user.getId())
                                        .addString(adrress.getState())
                                        .addString(adrress.getCity())
                                        .addString(adrress.getDistrict())
                                        .addString(adrress.getSecret())
                                        .addInteger(adrress.getNumber())
                                        .addInteger(adrress.getNumberAp())
                                        .addString(adrress.getLatitude())
                                        .addString(adrress.getLongitude())
                                        .addString(adrress.getSecret())
                                        .addBoolean(adrress.isActive())
                                        .addBoolean(adrress.isMain())
                          );    
                });

    }

    public void update(User user) {

        this.findById(user.getId()).onComplete(tr -> {
            
            if (tr.succeeded()) {
                
                for (Row row : tr.result()) {
        
                    client.preparedQuery("UPDATE DF_USER SET " + 
                                         "DOCUMENT = $1, EMAIL = $2, NAME = $3, PASSWORD = $4, " +
                                         "PHONE = $5, ACCEPT_TERMS = $6, ACTIVE = $7" + 
                                            " WHERE " +
                                         "ID = $8")
                          .execute(Tuple.tuple()
                                        .addString(user.getDocument().isEmpty() ? 
                                            row.getString("document") : user.getDocument())
                                        .addString(user.getEmail().isEmpty() ? 
                                            row.getString("email") : user.getEmail())
                                        .addString(user.getName().isEmpty() ? 
                                            row.getString("name") : user.getName())
                                        .addString(user.getPassword().isEmpty() ? 
                                            row.getString("password") : user.getPassword())
                                        .addString(user.getPhone().isEmpty() ? 
                                            row.getString("phone") : user.getPhone())
                                        .addBoolean(user.isAcceptTerms() == (row.getBoolean("accept_terms")) ?
                                            row.getBoolean("accept_terms") : user.isAcceptTerms())
                                        .addBoolean(user.isActive() == row.getBoolean("active") ? 
                                            row.getBoolean("active") : user.isActive())
                                        .addInteger(user.getId())
                          );
        
                }
                
            } else {
                System.out.println("Failure: " + tr.cause().getMessage());
            }

        });

    }

    public void delete(Integer id) {

        client.preparedQuery("DELETE FROM DF_USER_ADDRESS" + 
                             " WHERE " + 
                             "ID_USER = $1")
              .execute(Tuple.of(id))
              .onComplete(tr -> {
                client.preparedQuery("DELETE FROM DF_USER" + 
                                     " WHERE " +
                                     "ID = $1")
                      .execute(Tuple.of(id));
               });

    }

    public Future<RowSet<Row>> findById(Integer id) {
        
        return client.preparedQuery("SELECT * FROM DF_USER WHERE ID = $1")
              .execute(Tuple.of(id));

    }
    
}
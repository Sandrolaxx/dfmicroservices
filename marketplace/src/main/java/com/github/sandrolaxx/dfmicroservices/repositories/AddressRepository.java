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
public class AddressRepository {

    @Inject
    PgPool client;

    public void persist(User user) {

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

    }

    public void update(User user) {

        Address adrress = user.getAddress().get(0);

        this.findById(user.getId(), adrress.getId()).onComplete(tr -> {
            
            if (tr.succeeded()) {
                
                for (Row row : tr.result()) {
                    client.preparedQuery("UPDATE DF_USER_ADDRESS SET "                           + 
                                         "STATE = $1, CITY = $2, DISTRICT = $3, STREET = $4, "   +
                                         "NUMBER = $5, NUMBER_AP = $6, LATITUDE = $7, "          + 
                                         "LONGITUDE = $8, ACTIVE = $9, MAIN = $10"               + 
                                            " WHERE "                                            +
                                        "ID_USER = $11"                                          +
                                            " AND "                                              +
                                        "ID = $12")
                          .execute(Tuple.tuple()
                                        .addString(adrress.getState().isEmpty() ? 
                                            row.getString("state") : adrress.getState())
                                        .addString(adrress.getCity().isEmpty() ? 
                                            row.getString("city") : adrress.getCity())
                                        .addString(adrress.getDistrict().isEmpty() ? 
                                            row.getString("district") : adrress.getDistrict())
                                        .addString(adrress.getStreet().isEmpty() ? 
                                            row.getString("street") : adrress.getStreet())
                                        .addInteger(adrress.getNumber() == null ? 
                                            row.getInteger("number") : adrress.getNumber())
                                        .addInteger(adrress.getNumberAp() == null ? 
                                            row.getInteger("number_ap") : adrress.getNumberAp())
                                        .addString(adrress.getLatitude().isEmpty() ? 
                                            row.getString("latitude") : adrress.getLatitude())
                                        .addString(adrress.getLongitude().isEmpty() ? 
                                            row.getString("longitude") : adrress.getLongitude())
                                        .addBoolean(adrress.isActive() == row.getBoolean("active") ?
                                            row.getBoolean("active") : adrress.isActive())
                                        .addBoolean(adrress.isMain() == row.getBoolean("main") ? 
                                            row.getBoolean("main") : adrress.isMain())
                                        .addInteger(user.getId())
                                        .addInteger(adrress.getId())
                          );
        
                }
                
            } else {
                System.out.println("Failure: " + tr.cause().getMessage());
            }

        });

    }

    public void delete(User user) {

        Address adrress = user.getAddress().get(0);

        client.preparedQuery("DELETE FROM DF_USER_ADDRESS" + 
                                " WHERE "   +
                             "ID_USER = $1" +
                                " AND "     +
                             "ID = $2")
              .execute(Tuple.of(user.getId(), adrress.getId()));

    }

    public Future<RowSet<Row>> findById(Integer idUser, Integer idAddress) {
        
        return client.preparedQuery("SELECT * FROM DF_USER_ADDRESS" + 
                                        " WHERE "  +
                                    "ID_USER = $1" +
                                        " AND "    +
                                    "ID = $2")
                     .execute(Tuple.of(idUser, idAddress));

    }
}

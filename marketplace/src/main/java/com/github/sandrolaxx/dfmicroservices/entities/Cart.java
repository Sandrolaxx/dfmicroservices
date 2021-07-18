package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;
import java.util.List;

import io.vertx.mutiny.sqlclient.Row;


public class Cart {

    private Integer id;

    private Integer idUser;

    private List<Integer> idProductCart;

    private Date createdAt;

    private boolean active;

    public static Cart from(Row row) {
        
        var cart = new Cart();

        cart.setId(row.getInteger("id"));
        cart.setIdUser(row.getInteger("id_user"));
        cart.setActive(row.getBoolean("active"));

        return cart;

    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public List<Integer> getIdProductCart() {
        return idProductCart;
    }

    public void setIdProductCart(List<Integer> idProductCart) {
        this.idProductCart = idProductCart;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}

package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;

public class ProductCart {

    private Integer id;

    private Cart cart;

    private int productId;

    private int quantity;

    private boolean removed;

    private Date createdAt;

    private Date updatedAt;

    public ProductCart() {
    }

    public ProductCart(Cart cart, int productId, int quantity, boolean removed) {
        this.cart = cart;
        this.productId = productId;
        this.quantity = quantity;
        this.removed = removed;
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isRemoved() {
        return this.removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}

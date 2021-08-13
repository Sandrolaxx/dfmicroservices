package com.github.sandrolaxx.dfmicroservices.dto;

import com.github.sandrolaxx.dfmicroservices.entities.Product;

public class ProductOrderDto {
    
    private Product product;

    private Integer quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
}

package com.github.sandrolaxx.dfmicroservices.dto;

import com.github.sandrolaxx.dfmicroservices.entities.Product;

public class ProductDto {

    public String name;

    public Double price;

    public Double discount;

    public String description;

    public String imageUri;

    public Boolean active;

    public ProductDto() {
    }

    public ProductDto(Product entity) {
        name = entity.getName();
        price = entity.getPrice();
        discount = entity.getDiscount();
        description = entity.getDescription();
        imageUri = entity.getImageUri();
        active = entity.getActive();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}

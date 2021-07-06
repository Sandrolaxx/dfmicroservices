package com.github.sandrolaxx.dfmicroservices.dto;

import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPlateCategory;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPlateSize;

public class ProductDto {

    private String name;

    private Double price;

    private Double discount;

    private String description;

    private String imageUri;

    private Boolean active;

    private EnumPlateSize plateSize;

    private EnumPlateCategory category;

    public ProductDto() {
    }

    public ProductDto(Product entity) {
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.discount = entity.getDiscount();
        this.description = entity.getDescription();
        this.imageUri = entity.getImageUri();
        this.active = entity.getActive();
        this.plateSize = entity.getPlateSize();
        this.category = entity.getCategory();
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

    public EnumPlateSize getPlateSize() {
        return this.plateSize;
    }

    public void setPlateSize(EnumPlateSize plateSize) {
        this.plateSize = plateSize;
    }

    public EnumPlateCategory getCategory() {
        return this.category;
    }

    public void setCategory(EnumPlateCategory category) {
        this.category = category;
    }

}

package com.github.sandrolaxx.dfmicroservices.dto;

import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPlateCategory;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPlateSize;

public class ProductListDto {

    private Long id;

    private String name;

    private Double price;

    private Double discount;

    private String description;

    private String imageUri;

    private Boolean active;

    private EnumPlateSize plateSize;

    private EnumPlateCategory category;

    private String createdAt;

    private String updatedAt;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return this.description;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}

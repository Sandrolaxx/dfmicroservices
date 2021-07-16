package com.github.sandrolaxx.dfmicroservices.entities;

import com.github.sandrolaxx.dfmicroservices.dto.ProductDto;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumMessageType;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPlateCategory;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPlateSize;

import io.vertx.sqlclient.Row;

public class Product {

    private Integer id;

    private String name;

    private Double price;

    private Double discount;

    private String description;

    private String imageUri;

    private Boolean active;

    private EnumPlateSize plateSize;

    private EnumPlateCategory category;

    private EnumMessageType messageType;

    public Product() {
    }

    public Product(ProductDto dto) {
        name = dto.getName();
        price = dto.getPrice();
        discount = dto.getDiscount();
        description = dto.getDescription();
        imageUri = dto.getImageUri();
        active = dto.isActive();
        plateSize = dto.getPlateSize();
    }

    public Product from(Row row){
        return null;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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

    public Boolean isActive() {
        return active;
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

    public EnumMessageType getMessageType() {
        return this.messageType;
    }

    public void setMessageType(EnumMessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "Product [category=" + category + ", description=" + description + ", discount=" + discount + ", id="
                + id + ", imageUri=" + imageUri + ", messageType=" + messageType + ", name=" + name + ", plateSize="
                + plateSize + ", price=" + price + "]";
    }

    

}

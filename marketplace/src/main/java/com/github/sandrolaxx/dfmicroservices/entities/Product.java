package com.github.sandrolaxx.dfmicroservices.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumMessageType;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPlateCategory;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPlateSize;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
@Cacheable
@Table(name = "DF_PRODUCT")
public class Product extends PanacheEntityBase {

    @Id
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "DISCOUNT")
    private Double discount;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGE_URI")
    private String imageUri;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Column(name = "PLATE_SIZE")
    @Enumerated(EnumType.STRING)
    private EnumPlateSize plateSize;

    @Column
    @Enumerated(EnumType.STRING)
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "DF_PLATE_CATEGORIES")
    @ElementCollection(targetClass = EnumPlateCategory.class)
    private List<EnumPlateCategory> categoryList;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Transient
    private EnumMessageType messageType;

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

    public List<EnumPlateCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<EnumPlateCategory> categoryList) {
        this.categoryList = categoryList;
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

    public EnumMessageType getMessageType() {
        return this.messageType;
    }

    public void setMessageType(EnumMessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "Product [description=" + description + ", discount=" + discount + ", id="
                + id + ", imageUri=" + imageUri + ", messageType=" + messageType + ", name=" + name + ", plateSize="
                + plateSize + ", price=" + price + "]";
    }

}

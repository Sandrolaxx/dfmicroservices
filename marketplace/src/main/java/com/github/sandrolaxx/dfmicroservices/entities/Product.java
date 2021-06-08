package com.github.sandrolaxx.dfmicroservices.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.sandrolaxx.dfmicroservices.dto.ProductDto;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
@Table(name = "DF_PRODUCT")
public class Product extends PanacheEntityBase {

  @Id
  public Integer id;

  @Column(name = "NAME")
  public String name;
  
  @Column(name = "PRICE")
  public Double price;
  
  @Column(name = "DISCOUNT")
  public Double discount;

  @Column(name = "DESCRIPTION")
  public String description;
  
  @Column(name = "IMAGE_URI")
  public String imageUri;

  @Column(name = "ACTIVE")
  public Boolean active;

  @Column(name = "CREATED_AT")
  public Date createdAt;

  @Column(name = "UPDATED_AT")
  public Date updatedAt;

  public Product() {
  }

  public Product(ProductDto dto) {
    name = dto.getName();
    price = dto.getPrice();
    discount = dto.getDiscount();
    description = dto.getDescription();
    imageUri = dto.getImageUri();
    active = dto.getActive();
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

  public Boolean getActive() {
    return this.active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

}

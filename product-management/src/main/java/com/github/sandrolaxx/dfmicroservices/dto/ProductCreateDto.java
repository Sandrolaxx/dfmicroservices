package com.github.sandrolaxx.dfmicroservices.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ProductCreateDto {

  @NotNull
  @Size(min = 3, max = 40)
  public String name;

  @NotNull
  @Positive
  public Double price;

  @NotNull
  @Size(min = 5, max = 90)
  public String description;

  public String imageUri;

  @NotNull
  public Boolean active;

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

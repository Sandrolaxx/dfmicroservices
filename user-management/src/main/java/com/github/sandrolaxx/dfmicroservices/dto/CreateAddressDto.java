package com.github.sandrolaxx.dfmicroservices.dto;

public class CreateAddressDto {

  public String state;

  public String city;

  public String district;

  public String street;

  public Integer number;

  public Integer numberAp;

  public Double latitude;

  public Double longitude;

  public boolean active;

  public boolean main;

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getDistrict() {
    return this.district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getStreet() {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Integer getNumber() {
    return this.number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Integer getNumberAp() {
    return this.numberAp;
  }

  public void setNumberAp(Integer numberAp) {
    this.numberAp = numberAp;
  }

  public Double getLatitude() {
    return this.latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return this.longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isMain() {
    return this.main;
  }

  public void setMain(boolean main) {
    this.main = main;
  }

}

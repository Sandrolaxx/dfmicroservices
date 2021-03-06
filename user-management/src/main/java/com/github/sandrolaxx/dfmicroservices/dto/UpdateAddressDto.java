package com.github.sandrolaxx.dfmicroservices.dto;

public class UpdateAddressDto {

    private String state;

    private String city;

    private String district;

    private String street;

    private Integer number;

    private Integer numberAp;

    private String latitude;

    private String longitude;

    private boolean main;

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

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isMain() {
        return this.main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

}

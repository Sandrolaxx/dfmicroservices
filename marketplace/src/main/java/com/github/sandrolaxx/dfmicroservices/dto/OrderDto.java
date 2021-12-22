package com.github.sandrolaxx.dfmicroservices.dto;

import java.util.List;

import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumOrderStatus;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPaymentType;

public class OrderDto {

    private Integer orderId;
    
    private List<ProductOrderDto> productsOrder;

    private EnumOrderStatus orderStatus;
    
    private EnumPaymentType payType;

    private String district;

    private String street;
    
    private Integer number;

    private Integer numberAp;

    private Double latitude;

    private Double longitude;

    private Double deliveryValue;
    
    private Double total;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<ProductOrderDto> getProductsOrder() {
        return productsOrder;
    }

    public void setProductsOrder(List<ProductOrderDto> productsOrder) {
        this.productsOrder = productsOrder;
    }

    public EnumOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(EnumOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public EnumPaymentType getPayType() {
        return payType;
    }

    public void setPayType(EnumPaymentType payType) {
        this.payType = payType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDeliveryValue() {
        return deliveryValue;
    }

    public void setDeliveryValue(Double deliveryValue) {
        this.deliveryValue = deliveryValue;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumberAp() {
        return numberAp;
    }

    public void setNumberAp(Integer numberAp) {
        this.numberAp = numberAp;
    }
    
}

package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;

import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumOrderStatus;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPaymentType;

public class Order {

    private Integer id;

    private User user;

    private EnumOrderStatus orderStatus;

    private Double deliveryValue;

    private EnumPaymentType paymentType;
    
    private String addressDescription;
    
    private Double lititude;
    
    private Double longitude;
    
    private Double total;

    public Date createdAt;

    public Date updatedAt;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EnumOrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(EnumOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getDeliveryValue() {
        return this.deliveryValue;
    }

    public void setDeliveryValue(Double deliveryValue) {
        this.deliveryValue = deliveryValue;
    }

    public EnumPaymentType getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(EnumPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getAddressDescription() {
        return this.addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
    }

    public Double getLititude() {
        return this.lititude;
    }

    public void setLititude(Double lititude) {
        this.lititude = lititude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

}

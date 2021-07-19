package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumOrderStatus;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPaymentType;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
@Table(name = "DF_ORDER")
public class Order extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "ID_ORDER", sequenceName = "GEN_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ORDER")
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    private User user;

    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private EnumOrderStatus orderStatus;

    @Column(name = "DELIVERY_VALUE")
    private Double deliveryValue;

    @Column(name = "PAYMENT_TYPE")
    @Enumerated(EnumType.STRING)
    private EnumPaymentType paymentType;
    
    @Column(name = "ADDRESS_DESCRIPTION")
    private String addressDescription;
    
    @Column(name = "LATITUDE")
    private Double lititude;
    
    @Column(name = "LONGITUDE")
    private Double longitude;
    
    @Column(name = "TOTAL")
    private Double total;

    @Column(name = "CREATED_AT")
    public Date createdAt;

    @Column(name = "UPDATED_AT")
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

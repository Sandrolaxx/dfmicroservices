package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumOrderStatus;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPaymentType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

@Entity
@Table(name = "DF_ORDER")
public class Order extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "ID_ORDER", sequenceName = "GEN_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ORDER")
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private List<ProductCart> productOrderList;

    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private EnumOrderStatus orderStatus;

    @Column(name = "DELIVERY_VALUE")
    private Double deliveryValue;

    @Column(name = "PAYMENT_TYPE")
    @Enumerated(EnumType.STRING)
    private EnumPaymentType paymentType;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ADDRESS", referencedColumnName = "ID")
    private Address address;
    
    @Column(name = "TOTAL")
    private Double total;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    public Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    public Date updatedAt;

    public Order() {
    }

    public static Uni<List<Order>> findAllOrdersByidUser(Integer idUser, EnumOrderStatus orderStatus) {
        
        Map<String, Object> params = new HashMap<>();
        String query = "user.id = :idUser";

        params.put("idUser", idUser);
        
        if (orderStatus != null) {
            params.put("orderStatus", orderStatus);
            query = "user.id = :idUser and orderStatus = :orderStatus";
        }

        return find(query, Sort.descending("createdAt"), params).list();
        
    }

    public static Uni<List<Order>> findAllOrdersByStatus(EnumOrderStatus orderStatus) {
        
        Map<String, Object> params = new HashMap<>();

        if (orderStatus != null) {
            params.put("orderStatus", orderStatus);
            var query = "orderStatus = :orderStatus";

            return find(query, Sort.ascending("createdAt"), params).list();
        }

        return findAll(Sort.ascending("createdAt")).list();
    }

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public List<ProductCart> getProductOrderList() {
        return productOrderList;
    }

    public void setProductOrderList(List<ProductCart> productOrderList) {
        this.productOrderList = productOrderList;
    }

}

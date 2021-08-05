package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "DF_PRODUCT_CART")
@JsonIgnoreProperties({"cart"})
public class ProductCart extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "ID_DF_PRODUCT_CART", sequenceName = "GEN_DF_PRODUCT_CART", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_DF_PRODUCT_CART")
    @Column(name = "ID")
    private Integer id;

    @JoinColumn(name = "ID_CART", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;
    
    @JoinColumn(name = "ID_ORDER", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    
    @JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    public ProductCart() {
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

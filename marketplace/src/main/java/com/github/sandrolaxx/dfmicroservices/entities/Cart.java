package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
@Table(name = "DF_CART")
public class Cart extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "ID_CART", sequenceName = "GEN_CART", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_CART")
    @Column(name = "ID")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cart", orphanRemoval = true)
    private List<ProductCart> productCart;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "ACTIVE")
    private boolean active;

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

    public List<ProductCart> getProductCart() {
        return this.productCart;
    }

    public void setProductCart(List<ProductCart> productCart) {
        this.productCart = productCart;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}

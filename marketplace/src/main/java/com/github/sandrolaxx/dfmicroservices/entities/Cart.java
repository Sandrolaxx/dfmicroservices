package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;

@Entity
@Table(name = "DF_CART")
public class Cart extends PanacheEntityBase {

    @Id
    @Column(name = "ID")
    private String id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart", orphanRemoval = false)
    private List<ProductCart> productCartList;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "ACTIVE")
    private boolean active;

    public static Uni<Cart> findByUserId(Integer userId) {
        return find("user.id", userId).singleResult();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ProductCart> getProductCartList() {
        return productCartList;
    }

    public void setProductCartList(List<ProductCart> productCartList) {
        this.productCartList = productCartList;
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

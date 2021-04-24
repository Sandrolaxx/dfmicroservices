package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
@Table(name = "DF_PRODUCT_CART")
public class ProductCart extends PanacheEntityBase {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;

  @OneToOne
  @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
  public User user;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "ID_PRODUCT")
  public List<Product> productId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED_AT")
  public Date createdAt;

  @Column(name = "ACTIVE")
  public boolean active;

  public ProductCart(){
  }

}

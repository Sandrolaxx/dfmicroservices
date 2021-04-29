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
  public User user;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cart", orphanRemoval = true)
  public List<ProductCart> productCart;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED_AT")
  public Date createdAt;

  @Column(name = "ACTIVE")
  public boolean active;

  public Cart(){
  }

}

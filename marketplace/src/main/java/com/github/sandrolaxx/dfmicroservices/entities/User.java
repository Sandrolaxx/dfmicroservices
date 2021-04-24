package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
@Table(name = "DF_USER")
public class User extends PanacheEntityBase {

  @Id
  public Integer id;

  @Column(name = "NAME")
  public String name;

  @Column(name = "EMAIL")
  public String email;

  @Column(name = "PASSWORD")
  public String password;

  @Column(name = "DOCUMENT")
  public String document;

  @Column(name = "PHONE")
  public String phone;

  @Column(name = "ACTIVE")
  public boolean active;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "ID_USER")
  public List<Address> address;

  @Column(name = "CREATED_AT")
  public Date createdAt;

  @Column(name = "UPDATED_AT")
  public Date updatedAt;

  @OneToOne(cascade = CascadeType.ALL)
  private ProductCart productCart;

}

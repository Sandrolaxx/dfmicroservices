package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
@Table(name = "DF_USER_ADDRESS")
public class Address extends PanacheEntityBase {

  @Id
  public Integer id;

  @ManyToOne
  @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
  public User user;

  @Column(name = "STATE")
  public String state;

  @Column(name = "CITY")
  public String city;

  @Column(name = "DISTRICT")
  public String district;

  @Column(name = "STREET")
  public String street;

  @Column(name = "NUMBER")
  public Integer number;

  @Column(name = "NUMBER_AP")
  public Integer numberAp;

  @Column(name = "ACTIVE")
  public boolean active;

  @Column(name = "MAIN")
  public boolean main;

  @Column(name = "LATITUDE")
  public Double latitude;

  @Column(name = "LONGITUDE")
  public Double longitude;

  @Column(name = "CREATED_AT")
  public Date createdAt;

  @Column(name = "UPDATED_AT")
  public Date updatedAt;

}

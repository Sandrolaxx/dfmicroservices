package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "DF_PRODUCT")
public class Product extends PanacheEntityBase {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;//Panache já em tempo de execução coloca como private

  @Column(name = "NAME")
  public String name;
  
  @Column(name = "PRICE")
  public Double price; 
  
  @Column(name = "DESCRIPTION")
  public String description;
  
  @Column(name = "IMAGE_URI")
  public String imageUri;

  @Column(name = "ACTIVE")
  public Boolean active;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED_AT")
  public Date createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "UPDATED_AT")
  public Date updatedAt;

}

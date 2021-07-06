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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumMessageType;
import com.github.sandrolaxx.dfmicroservices.utils.EncryptUtil;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jboss.aerogear.security.otp.api.Base32;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "DF_USER")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DOCUMENT")
    private String document;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "ACCEPT_TERMS")
    private boolean acceptTerms;

    @Column(name = "SECRET")
    private String secret;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER")
    private List<Address> address;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Transient
    private EnumMessageType messageType;

    @PrePersist
    private void encryptSensitiveData() {
        this.document = EncryptUtil.textEncrypt(this.document, secret.substring(0, 16));
        this.email = EncryptUtil.textEncrypt(this.email, secret.substring(0, 16));
        this.password = EncryptUtil.textEncrypt(this.password, secret.substring(0, 16));
        this.phone = EncryptUtil.textEncrypt(this.phone, secret.substring(0, 16));
        this.name = EncryptUtil.textEncrypt(this.name, secret.substring(0, 16));
    }

    public User() {
        super();
        this.secret = Base32.random();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocument() {
        return this.document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAcceptTerms() {
        return this.acceptTerms;
    }

    public void setAcceptTerms(boolean acceptTerms) {
        this.acceptTerms = acceptTerms;
    }

    public List<Address> getAddress() {
        return this.address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
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

    public EnumMessageType getMessageType() {
        return this.messageType;
    }

    public void setMessageType(EnumMessageType messageType) {
        this.messageType = messageType;
    }

}

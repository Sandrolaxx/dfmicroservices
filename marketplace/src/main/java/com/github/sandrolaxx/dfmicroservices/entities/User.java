package com.github.sandrolaxx.dfmicroservices.entities;

import java.util.List;

import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumMessageType;

import io.vertx.mutiny.sqlclient.Row;


public class User {

    private Integer id;
    
    private String name;

    private String email;

    private String password;

    private String document;

    private String phone;

    private boolean active;

    private boolean acceptTerms;

    private String secret;

    private List<Address> address;

    private List<Order> orders;

    private EnumMessageType messageType;

    public User() {
    }

    public User(Integer id, String name, String email, String password, String document, 
            String phone, boolean acceptTerms, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.document = document;
        this.phone = phone;
        this.acceptTerms = acceptTerms;
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

    public Boolean isActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isAcceptTerms() {
        return this.acceptTerms;
    }

    public void setAcceptTerms(Boolean acceptTerms) {
        this.acceptTerms = acceptTerms;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<Address> getAddress() {
        return this.address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
    
    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public EnumMessageType getMessageType() {
        return this.messageType;
    }

    public void setMessageType(EnumMessageType messageType) {
        this.messageType = messageType;
    }

    public static User from(Row row) {
        return new User(
            row.getInteger("id"),
            row.getString("name"),
            row.getString("email"),
            row.getString("password"),
            row.getString("document"),
            row.getString("phone"),
            row.getBoolean("acceptTerms"),
            row.getBoolean("active"));
    }

    @Override
    public String toString() {
        return "User [id=" + id + " document="+ document + ", email=" + email 
                + ", id=" + id + ", messageType=" + messageType + ", name=" + name
                +", phone=" + phone + "]";
    }
    
}

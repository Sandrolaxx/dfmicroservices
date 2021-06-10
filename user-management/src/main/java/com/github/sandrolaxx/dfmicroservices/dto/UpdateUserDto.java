package com.github.sandrolaxx.dfmicroservices.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UpdateUserDto {

    private String name;

    @Email
    private String email;

    @Size(min = 5)
    private String password;

    @Size(max = 14)
    private String document;

    @Size(max = 15)
    private String phone;

    private boolean active;

    private boolean acceptTerms;

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

}

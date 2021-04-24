package com.github.sandrolaxx.dfmicroservices.dto;

import java.util.List;

public class CreateUserDto {

  public String name;

  public String email;

  public String password;

  public String document;

  public String phone;

  public boolean active;

  public List<CreateAddressDto> address;

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

  public boolean getActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public List<CreateAddressDto> getAddress() {
    return this.address;
  }

  public void setAddress(List<CreateAddressDto> address) {
    this.address = address;
  }
  
}

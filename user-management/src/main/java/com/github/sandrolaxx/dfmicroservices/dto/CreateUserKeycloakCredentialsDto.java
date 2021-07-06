package com.github.sandrolaxx.dfmicroservices.dto;

public class CreateUserKeycloakCredentialsDto {

    private String value;

    private boolean temporary;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getTemporary() {
        return this.temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

}
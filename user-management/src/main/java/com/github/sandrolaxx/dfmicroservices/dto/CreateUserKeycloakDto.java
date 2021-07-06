package com.github.sandrolaxx.dfmicroservices.dto;

import java.util.List;

public class CreateUserKeycloakDto {

    private String username;

    private List<CreateUserKeycloakCredentialsDto> credentials;

    private boolean enabled;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CreateUserKeycloakCredentialsDto> getCredentials() {
        return this.credentials;
    }

    public void setCredentials(List<CreateUserKeycloakCredentialsDto> credentials) {
        this.credentials = credentials;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
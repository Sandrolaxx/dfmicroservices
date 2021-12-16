package com.github.sandrolaxx.dfmicroservices.dto;

import java.util.List;
import java.util.Map;

public class CreateUserKeycloakDto {

    private String username;

    private Map<String, Integer> attributes;

    private List<CreateUserKeycloakCredentialsDto> credentials;

    private boolean enabled;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Integer> attributes) {
        this.attributes = attributes;
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
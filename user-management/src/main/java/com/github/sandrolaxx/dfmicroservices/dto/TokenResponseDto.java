package com.github.sandrolaxx.dfmicroservices.dto;

import javax.json.bind.annotation.JsonbProperty;

public class TokenResponseDto {

    @JsonbProperty("access_token")
    private String accessToken;

    @JsonbProperty("expires_in")
    private Integer expiresIn;

    @JsonbProperty("refresh_expires_in")
    private Integer refreshExpiresIn;

    @JsonbProperty("refresh_token")
    private String refreshToken;

    @JsonbProperty("token_type")
    private String tokenType;

    @JsonbProperty("session_state")
    private String sessionState;

    @JsonbProperty("scope")
    private String scope;

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Integer getRefreshExpiresIn() {
        return this.refreshExpiresIn;
    }

    public void setRefreshExpiresIn(Integer refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getSessionState() {
        return this.sessionState;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}

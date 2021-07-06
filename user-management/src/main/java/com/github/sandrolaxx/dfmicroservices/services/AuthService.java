package com.github.sandrolaxx.dfmicroservices.services;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Form;

import com.github.sandrolaxx.dfmicroservices.dto.TokenResponseDto;
import com.github.sandrolaxx.dfmicroservices.utils.RequestUtil;
import com.github.sandrolaxx.dfmicroservices.utils.RestClientKey;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Traced(operationName = "AuthService")
public class AuthService {

    @Inject
    @RestClient
    RestClientKey restClientKey;

    @ConfigProperty(name = "the-ashen-one-username")
    String username;
  
    @ConfigProperty(name = "the-ashen-one-password")
    String password;
  
    @ConfigProperty(name = "the-ashen-one-auth")
    String auth;

    public String getNewToken() {
        
        String token;

        var grantType = "password";
        var basicToken = RequestUtil.onlyBasic(auth);
    
        Form tokenReq = new Form()
        .param("grant_type", grantType)
        .param("username", username)
        .param("password", password);
        
        TokenResponseDto tokenDto = restClientKey.getNewToken(basicToken, tokenReq);
    
        token = tokenDto.getTokenType() + " " + tokenDto.getAccessToken();
        
        return token;
        
    }
}
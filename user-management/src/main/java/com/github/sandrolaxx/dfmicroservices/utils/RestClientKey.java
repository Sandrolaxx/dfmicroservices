package com.github.sandrolaxx.dfmicroservices.utils;


import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.sandrolaxx.dfmicroservices.dto.CreateUserKeycloakDto;
import com.github.sandrolaxx.dfmicroservices.dto.TokenResponseDto;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:9091/auth")
public interface RestClientKey {
    
  @POST
  @Path("/admin/realms/dfmicroservices/users")
  Response createUserKeycloak(@HeaderParam("Authorization") String tokenKeycloak,
      CreateUserKeycloakDto dto);
  
  @POST
  @Path("/realms/dfmicroservices/protocol/openid-connect/token")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  TokenResponseDto getNewToken(@HeaderParam("Authorization") String basicToken, Form tokenForm);

}
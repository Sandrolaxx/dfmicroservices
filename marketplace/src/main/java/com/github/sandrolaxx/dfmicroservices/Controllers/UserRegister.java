package com.github.sandrolaxx.dfmicroservices.Controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sandrolaxx.dfmicroservices.entities.User;
import com.github.sandrolaxx.dfmicroservices.repositories.AddressRepository;
import com.github.sandrolaxx.dfmicroservices.repositories.UserRepository;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgPool;

@ApplicationScoped
public class UserRegister {

    @Inject
    PgPool client;

    @Inject
    UserRepository repository;

    @Inject
    AddressRepository addressRepository;

    @Incoming("user")
    public void receivedNewUser(JsonObject userPropagated) throws JsonMappingException, JsonProcessingException, InterruptedException {

        userPropagated.remove("createdAt");
        userPropagated.remove("updatedAt");
        
        if (userPropagated.getJsonArray("address") != null) {
            userPropagated.getJsonArray("address").getJsonObject(0).remove("updatedAt");
            userPropagated.getJsonArray("address").getJsonObject(0).remove("createdAt");
        }

        var m = new ObjectMapper();
        var user = m.readValue(userPropagated.toString(), User.class);
        
        switch (user.getMessageType()) {
            case CREATE:
                repository.persist(user);
                break;
            case UPDATE:
                repository.update(user);
                break;
            case DELETE:
                repository.delete(user);    
                break;
            case CREATE_ADDRESS:
                addressRepository.persist(user);
                break;            
            case UPDATE_ADDRESS:
                addressRepository.update(user);
                break;            
            case DELETE_ADDRESS:
                addressRepository.delete(user);
                break;            
            default:
                break;
            }
            
        }

}

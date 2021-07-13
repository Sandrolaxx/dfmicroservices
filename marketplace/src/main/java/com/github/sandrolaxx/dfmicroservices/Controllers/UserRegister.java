package com.github.sandrolaxx.dfmicroservices.Controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sandrolaxx.dfmicroservices.entities.User;
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
                repository.delete(user.getId());    
                break;
            case UPDATE_ADDRESS:
                this.updateAddress(user);
                break;            
            default:
                break;
            }
            
        }

        private void updateAddress(User user) {
            
            var updateAddress = user.getAddress().get(0);

            System.out.println(updateAddress.getCity());

            /**@TODO
             * Implement
             */

        }


}

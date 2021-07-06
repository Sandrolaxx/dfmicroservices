package com.github.sandrolaxx.dfmicroservices.Controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sandrolaxx.dfmicroservices.entities.User;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.hibernate.reactive.mutiny.Mutiny;
import org.jboss.logging.Logger;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
@ActivateRequestContext
public class UserRegister {

    @Inject
    Mutiny.Session mutinySession;

    Logger log = Logger.getLogger(UserRegister.class);

    @Incoming("user")
    @Transactional
    @Blocking
    public void receivedNewUser(JsonObject userPropagated) throws JsonMappingException, JsonProcessingException {

        var m = new ObjectMapper();
        var userMessage = m.readValue(userPropagated.toString(), User.class);
        
        var messageType = userMessage.getMessageType();

        switch (messageType) {
            case CREATE:
                this.createUser(userMessage);
                break;
            case UPDATE:
                this.updateUser(userMessage);
                break;
            case DELETE:
                this.deleteUser(userMessage);
                break;
            case CREATE_ADDRESS:
                this.createAddress(userMessage);
                break;
            case UPDATE_ADDRESS:
                this.updateAddress(userMessage);
                break;
            case DELETE_ADDRESS:
            this.deleteAddress(userMessage);
                break;
            default:
                break;
            }
            
            
        }
        
        private void createUser(User user) {
            
            mutinySession.persist(user)
            .chain(mutinySession::flush)
            .await()
            .indefinitely();
            
        }
        
        private void updateUser(User user) {
        
        var existsUser = mutinySession.find(User.class, user.getId())
                                       .await()
                                       .indefinitely();

        existsUser.setName(user.getName() == null ? existsUser.getName() : user.getName());
        existsUser.setEmail(user.getEmail() == null ? existsUser.getEmail() : user.getEmail());
        existsUser.setPhone(user.getPhone() == null ? existsUser.getPhone() : user.getPhone());
        existsUser.setDocument(user.getDocument() == null ? existsUser.getDocument() : user.getDocument());
        existsUser.setPassword(user.getPassword() == null ? existsUser.getPassword() : user.getPassword());
        existsUser.setActive(user.isActive() == existsUser.isActive() ? existsUser.isActive() : user.isActive());
        existsUser.setAcceptTerms(
                user.isAcceptTerms() == existsUser.isAcceptTerms() ? existsUser.isAcceptTerms() : user.isAcceptTerms());                    
        
                
        mutinySession.persist(existsUser)
                     .chain(mutinySession::flush)
                     .await()
                     .indefinitely();                               

    }

    private void deleteUser(User user) {

        var existsUser = mutinySession.find(User.class, user.getId())
                                       .await()
                                       .indefinitely();

        mutinySession.remove(existsUser)
                     .chain(mutinySession::flush)
                     .await()
                     .indefinitely();                               

    }

    private void createAddress(User user) {

        var existsUser  = mutinySession.find(User.class, user.getId())
                                       .await()
                                       .indefinitely();

        var createAddress = user.getAddress().get(0);

        createAddress.setUser(existsUser);

        mutinySession.persist(createAddress)
                    .chain(mutinySession::flush)
                    .await()
                    .indefinitely();

    }

    private void updateAddress(User user) {

        var existsUser = mutinySession.find(User.class, user.getId())
                                      .await()
                                      .indefinitely();

        var createAddress = user.getAddress().get(0);
        
        var adr = Uni.createFrom().item(existsUser.getAddress())
                          .map(a -> a.stream()
                                     .filter(b -> b.getId().equals(createAddress.getId()))
                                     .findFirst()
                                     .get())
                          .invoke(a -> {
                                                             
                    a.setState(createAddress.getState() == null ? a.getState() : createAddress.getState());
                    a.setCity(createAddress.getCity() == null ? a.getCity() : createAddress.getCity());
                    a.setNumber(createAddress.getNumber() == null ? a.getNumber() : createAddress.getNumber());
                    a.setNumberAp(createAddress.getNumberAp() == null ? a.getNumberAp() : createAddress.getNumberAp());
                    a.setMain(createAddress.isMain() == a.isMain() ? a.isMain() : createAddress.isMain());
                    a.setDistrict(createAddress.getDistrict() == null ? a.getDistrict() : createAddress.getDistrict());
                    a.setLatitude(createAddress.getLatitude() == null ? a.getLatitude() : createAddress.getLatitude());
                    a.setLatitude(createAddress.getLatitude() == null ? a.getLatitude() : createAddress.getLatitude());
                    a.setStreet(createAddress.getStreet() == null ? a.getStreet() : createAddress.getStreet());

                });

                adr.onItem().invoke(
                    a -> mutinySession.persist(a)
                                      .chain(mutinySession::flush)
                                      . await()
                                      .indefinitely());
                         

    }

    private void deleteAddress(User user) {

        var existsUser = mutinySession.find(User.class, user.getId())
                                       .await()
                                       .indefinitely();

        var addressToDelete = user.getAddress().get(0);

        for (var adr : existsUser.getAddress()) {
            if (adr.getId().equals(addressToDelete.getId())) {
                addressToDelete = adr;
            }
        } 
        
        existsUser.getAddress().remove(addressToDelete);

        mutinySession.remove(addressToDelete)
                     .chain(mutinySession::flush)
                     .await()
                     .indefinitely(); 

    }
}

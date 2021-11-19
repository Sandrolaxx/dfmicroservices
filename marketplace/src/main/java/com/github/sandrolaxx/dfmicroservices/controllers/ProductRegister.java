package com.github.sandrolaxx.dfmicroservices.controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.repositories.ProductRepository;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class ProductRegister {

    @Inject
    ProductRepository repository;

    @Incoming("product")
    public void receivedProduct(JsonObject productPropagated) throws JsonMappingException, JsonProcessingException {

        productPropagated.remove("createdAt");
        productPropagated.remove("updatedAt");
        
        ObjectMapper m = new ObjectMapper();
        Product product = m.readValue(productPropagated.toString(), Product.class);

        switch (product.getMessageType()) {
            case CREATE:
                repository.persist(product);
            break;
            case UPDATE:
                repository.update(product);
            break;
            case DELETE:
                repository.delete(product);
                break;
            default:
                break;
        }
        
    }

}

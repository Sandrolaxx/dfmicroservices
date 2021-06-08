package com.github.sandrolaxx.dfmicroservices.Controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sandrolaxx.dfmicroservices.entities.Product;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.hibernate.reactive.mutiny.Mutiny;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
@ActivateRequestContext
public class ProductRegister {

    @Inject
    Mutiny.Session mutinySession;

    @Incoming("product")
    @Transactional
    @Blocking
    public void receivedProduct(JsonObject productPropagated) throws JsonMappingException, JsonProcessingException {

        ObjectMapper m = new ObjectMapper();
        Product newProduct = m.readValue(productPropagated.toString(), Product.class);

        mutinySession.persist(newProduct).chain(mutinySession::flush).await().indefinitely();

    }
}

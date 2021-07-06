package com.github.sandrolaxx.dfmicroservices.Controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumMessageType;

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
        Product productMessage = m.readValue(productPropagated.toString(), Product.class);

        EnumMessageType messageType = productMessage.getMessageType();

        switch (messageType) {
            case CREATE:
                this.persistNewProduct(productMessage);
                break;
            case UPDATE:
                this.updateProduct(productMessage);
                break;
            case DELETE:
                this.deleteProduct(productMessage);
                break;
            default:
                break;
        }

        
    }
    
    private void persistNewProduct(Product product) {

        mutinySession.persist(product)
                     .chain(mutinySession::flush)
                     .await()
                     .indefinitely();

    }

    private void updateProduct(Product product) {

        Product existProduct = mutinySession.find(Product.class, product.getId())
                                            .await()
                                            .indefinitely();

        existProduct.setName(product.getName() == null ? existProduct.getName() : product.getName());
        existProduct.setPrice(product.getPrice() == null ? existProduct.getPrice() : product.getPrice());
        existProduct.setDiscount(product.getDiscount() == null ? existProduct.getDiscount() : product.getDiscount());
        existProduct.setDescription(product.getDescription() == null ? existProduct.getDescription() : product.getDescription());
        existProduct.setImageUri(product.getImageUri() == null ? existProduct.getImageUri() : product.getDescription());
        existProduct.setActive(product.getActive() == existProduct.getActive() ? existProduct.getActive() : product.getActive());
        existProduct.setPlateSize(product.getPlateSize() == null ? existProduct.getPlateSize() : product.getPlateSize());
        existProduct.setCategory(product.getCategory() == null ? existProduct.getCategory() : product.getCategory());                                  

        mutinySession.merge(existProduct)
                     .chain(mutinySession::flush)
                     .await()
                     .indefinitely();

    }

    private void deleteProduct(Product product) {

        Product deleteProduct = mutinySession.find(Product.class, product.getId())
                                             .await()
                                             .indefinitely();

        mutinySession.remove(deleteProduct)
                     .chain(mutinySession::flush)
                     .await()
                     .indefinitely();

    }
    
}

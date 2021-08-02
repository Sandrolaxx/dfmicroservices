package com.github.sandrolaxx.dfmicroservices.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import com.github.sandrolaxx.dfmicroservices.dto.ListProductCart;

@ApplicationScoped
public class MpService {
    
    public Response addProductsToCart(Integer idCart, ListProductCart listProducts) {
        return Response.ok().build();
    }
    
}

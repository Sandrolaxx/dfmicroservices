package com.github.sandrolaxx.dfmicroservices.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.sandrolaxx.dfmicroservices.entities.Cart;
import com.github.sandrolaxx.dfmicroservices.entities.ProductCart;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumOrderStatus;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumPaymentType;
import com.github.sandrolaxx.dfmicroservices.services.MpService;
import com.github.sandrolaxx.dfmicroservices.utils.ValidateUtil;

import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;

@Path("/dona-frost/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Marketplace")
@RolesAllowed({"users", "Admin"})
@SecurityScheme(securitySchemeName = "microservices", type = SecuritySchemeType.OAUTH2, flows = 
@OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:9091/auth/realms/DonaFrost/protocol/openid-connect/token")))
public class MpController {

    @Inject
    MpService service;

    @Inject
    SecurityIdentity identity;

    @GET
    @Path("/cart")
    public Uni<List<ProductCart>> listProductCartByIdCart() {
        
        Uni<Cart> cart = service.resolveIdCart(identity);

        return cart.onItem()
                   .transform(c -> c.getProductCartList());

    }

    @POST
    @Path("/cart")
    public Uni<Response> addProductOnCart(@HeaderParam("idProduct") Integer idProduct, 
            @HeaderParam("quantity") Integer quantity) {
        var cart = service.resolveIdCart(identity);

        ValidateUtil.validateNewProductOnCart(idProduct, quantity);            
        return service.addProductToCart(cart, idProduct, quantity);
    }

    @PUT
    @Path("/cart")
    public Uni<Response> addQuantityToProductCart(@HeaderParam("idProductCart") Integer idProductCart, 
                @HeaderParam("quantity") Integer quantity) {
        ValidateUtil.validateAddQuantity(idProductCart, quantity);            
        return service.addQuantityToProductCart(idProductCart, quantity);
    }

    @DELETE
    @Path("/cart")
    public Uni<Response> removeQuantityToProductCart(@HeaderParam("idProductCart") Integer idProductCart, 
                @HeaderParam("quantity") Integer quantity) {
        ValidateUtil.validateRemoveQuantity(idProductCart, quantity);            
        return service.removeQuantityToProductCart(idProductCart, quantity);
    }

    @GET
    @Path("/order")
    public Uni<Response> listOrders(@HeaderParam("orderStatus") EnumOrderStatus orderStatus) {
        var userId = service.resolveUserId(identity);

        return service.listOrders(userId, orderStatus);
    }

    @POST
    @Path("/order")
    public Uni<Response> cartToOrder(@HeaderParam("payType") EnumPaymentType payType) {
        var cart = service.resolveIdCart(identity);

        ValidateUtil.validateNewOrder(payType);           
        return service.cartToOrder(cart, payType);
    }

}
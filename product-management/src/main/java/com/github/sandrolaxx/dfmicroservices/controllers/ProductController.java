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
import javax.ws.rs.core.Response.Status;

import com.github.sandrolaxx.dfmicroservices.dto.ProductCreateDto;
import com.github.sandrolaxx.dfmicroservices.dto.ProductListDto;
import com.github.sandrolaxx.dfmicroservices.entities.Product;
import com.github.sandrolaxx.dfmicroservices.services.ProductService;
import com.github.sandrolaxx.dfmicroservices.utils.ErrorResponse;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/dona-frost/v1/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Product")
@RolesAllowed("dfmicroservices")
@SecurityScheme(securitySchemeName = "df-product-oauth", type = SecuritySchemeType.OAUTH2, flows = 
@OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:9091/auth/realms/dfmicroservices/protocol/openid-connect/token")))
public class ProductController {

    @Inject
    ProductService productService;

    @Inject
    @Channel("product")
    Emitter<Product> emitter;

    @GET
    @APIResponse(responseCode = "200", description = "Caso sucesso, retorna a lista de produtos")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ErrorResponse.class)))
    @Counted(name = "Quantidade chamadas produto")
    @SimplyTimed(name = "Tempo simples/médio de busca")
    @Timed(name = "Tempo completo da busca")
    public List<ProductListDto> listAll() {
        return productService.findAll();
    }

    @POST
    @APIResponse(responseCode = "201", description = "Caso seja cadastrado com sucesso.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ErrorResponse.class)))
    public Response addProduct(ProductCreateDto dto) {
        
        Product newProduct = productService.persistProduct(dto);

        emitter.send(newProduct);

        return Response.status(Status.CREATED).build();
    }

    @PUT
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ErrorResponse.class)))
    public void updateProduct(@HeaderParam("id") Integer id, ProductCreateDto dto) {
        productService.updateProduct(id, dto);
    }

    @DELETE
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ErrorResponse.class)))
    public void deleteProduct(@HeaderParam("id") Integer id) {
        productService.deleteProduct(id);
    }
}
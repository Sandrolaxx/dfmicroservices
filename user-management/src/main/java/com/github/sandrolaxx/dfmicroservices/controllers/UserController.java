package com.github.sandrolaxx.dfmicroservices.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.sandrolaxx.dfmicroservices.dto.CreateUserDto;
import com.github.sandrolaxx.dfmicroservices.dto.ListUserDto;
import com.github.sandrolaxx.dfmicroservices.dto.UpdateAddressDto;
import com.github.sandrolaxx.dfmicroservices.dto.UpdateUserDto;
import com.github.sandrolaxx.dfmicroservices.entities.User;
import com.github.sandrolaxx.dfmicroservices.services.UserService;
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

@Path("/dona-frost/v1/user")
@Tag(name = "User")
@RolesAllowed("dfmicroservices")
@SecurityScheme(securitySchemeName = "df-product-oauth", type = SecuritySchemeType.OAUTH2, flows = 
@OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:9091/auth/realms/dfmicroservices/protocol/openid-connect/token")))
public class UserController {

    @Inject
    UserService userService;

    @Inject
    @Channel("user")
    Emitter<User> emitter;

    @GET
    @APIResponse(responseCode = "200", description = "Caso sucesso, retorna a lista de usuários")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ErrorResponse.class)))
    @Counted(name = "Quantidade chamadas listagem usuários")
    @SimplyTimed(name = "Tempo simples/médio de busca")
    @Timed(name = "Tempo completo da busca")
    public List<ListUserDto> listAll() {
        return userService.findAll();
    }

    @POST
    @APIResponse(responseCode = "201", description = "Caso seja cadastrado com sucesso.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ErrorResponse.class)))
    public Response addUser(@Valid CreateUserDto dto) {

        User newUser = userService.persistUser(dto);

        emitter.send(newUser);

        return Response.status(Status.CREATED).build();

    }

    @PUT
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ErrorResponse.class)))
    public void updateUser(@HeaderParam("idUser") Integer idUser, @Valid UpdateUserDto dto) {
        userService.updateUser(idUser, dto);
    }

    @DELETE
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ErrorResponse.class)))
    public void deleteUser(@HeaderParam("idUser") Integer idUser) {
        userService.deleteUser(idUser);
    }

    @PUT
    @Path("/address")
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ErrorResponse.class)))
    public void updateAddres(@HeaderParam("idUser") Integer idUser, @HeaderParam("idAddress") Integer idAddress,
            UpdateAddressDto dto) {
        userService.updateAddress(idUser, idAddress, dto);
    }

    @DELETE
    @Path("/address")
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ErrorResponse.class)))
    public void deleteAddress(@HeaderParam("idUser") Integer idUser, @HeaderParam("idAddress") Integer idAddress) {
        userService.deleteAddress(idUser, idAddress);
    }

}
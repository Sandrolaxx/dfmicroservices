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

import com.github.sandrolaxx.dfmicroservices.dto.CreateAddressDto;
import com.github.sandrolaxx.dfmicroservices.dto.CreateUserDto;
import com.github.sandrolaxx.dfmicroservices.dto.FrostExceptionResponseDto;
import com.github.sandrolaxx.dfmicroservices.dto.ListUserDto;
import com.github.sandrolaxx.dfmicroservices.dto.UpdateAddressDto;
import com.github.sandrolaxx.dfmicroservices.dto.UpdateUserDto;
import com.github.sandrolaxx.dfmicroservices.entities.User;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumMessageType;
import com.github.sandrolaxx.dfmicroservices.services.UserService;

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
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "User")
@RolesAllowed("Admin")
@SecurityScheme(securitySchemeName = "microservices", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = 
@OAuthFlow(tokenUrl = "http://localhost:9091/auth/realms/DonaFrost/protocol/openid-connect/token")))
public class UserController {

    @Inject
    UserService userService;

    @Inject
    @Channel("user")
    Emitter<User> emitter;

    @GET
    @APIResponse(responseCode = "200", description = "Caso sucesso, retorna a lista de usuários")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = FrostExceptionResponseDto.class)))
    @Counted(name = "Quantidade chamadas listagem usuários")
    @SimplyTimed(name = "Tempo simples/médio de busca")
    @Timed(name = "Tempo completo da busca")
    public List<ListUserDto> listAll() {
        return userService.findAll();
    }

    @POST
    @APIResponse(responseCode = "201", description = "Caso seja cadastrado com sucesso.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = FrostExceptionResponseDto.class)))
    public Response createUser(CreateUserDto dto) {

        var user = userService.persistUser(dto);

        user.setMessageType(EnumMessageType.CREATE);
        
        emitter.send(user);
        
        return Response.status(Status.CREATED).build();
        
    }
    
    @PUT
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = FrostExceptionResponseDto.class)))
    public void updateUser(@HeaderParam("idUser") Integer idUser, UpdateUserDto dto) {
        
        var user = userService.updateUser(idUser, dto);

        user.setMessageType(EnumMessageType.UPDATE);
        user.setAddress(null);

        emitter.send(user);

    }

    @DELETE
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = FrostExceptionResponseDto.class)))
    public void deleteUser(@HeaderParam("idUser") Integer idUser) {
        
        var user = userService.deleteUser(idUser);

        user.setMessageType(EnumMessageType.DELETE);

        emitter.send(user);

    }

    @POST
    @Path("/address")
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = FrostExceptionResponseDto.class)))
    public void createAddress(@HeaderParam("idUser") Integer idUser, CreateAddressDto address) {
        
        var adr = userService.createAddress(idUser, address);
        var user = userService.defaultUserToPropagate(idUser, adr);

        user.setMessageType(EnumMessageType.CREATE_ADDRESS);

        emitter.send(user);

    }

    @PUT
    @Path("/address")
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = FrostExceptionResponseDto.class)))
    public void updateAddress(@HeaderParam("idUser") Integer idUser, @HeaderParam("idAddress") Integer idAddress,
            UpdateAddressDto dto) {

        var adr = userService.updateAddress(idUser, idAddress, dto);
        var user = userService.defaultUserToPropagate(idUser, adr);

        user.setMessageType(EnumMessageType.UPDATE_ADDRESS);

        emitter.send(user);

    }

    @DELETE
    @Path("/address")
    @APIResponse(responseCode = "204", description = "Caso sucesso, não retorna conteúdo.")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = FrostExceptionResponseDto.class)))
    public void deleteAddress(@HeaderParam("idUser") Integer idUser, @HeaderParam("idAddress") Integer idAddress) {
        
        var user = userService.deleteAddress(idUser, idAddress);

        user.setMessageType(EnumMessageType.DELETE_ADDRESS);

        emitter.send(user);

    }

}
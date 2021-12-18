package com.github.sandrolaxx.dfmicroservices.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonNumber;
import javax.transaction.Transactional;

import com.github.sandrolaxx.dfmicroservices.dto.CreateAddressDto;
import com.github.sandrolaxx.dfmicroservices.dto.CreateUserDto;
import com.github.sandrolaxx.dfmicroservices.dto.CreateUserKeycloakCredentialsDto;
import com.github.sandrolaxx.dfmicroservices.dto.CreateUserKeycloakDto;
import com.github.sandrolaxx.dfmicroservices.dto.ListUserDto;
import com.github.sandrolaxx.dfmicroservices.dto.UpdateAddressDto;
import com.github.sandrolaxx.dfmicroservices.dto.UpdateUserDto;
import com.github.sandrolaxx.dfmicroservices.entities.Address;
import com.github.sandrolaxx.dfmicroservices.entities.User;
import com.github.sandrolaxx.dfmicroservices.entities.enums.EnumErrorCode;
import com.github.sandrolaxx.dfmicroservices.mapper.IUserMapper;
import com.github.sandrolaxx.dfmicroservices.utils.EncryptUtil;
import com.github.sandrolaxx.dfmicroservices.utils.FrostException;
import com.github.sandrolaxx.dfmicroservices.utils.RestClientKey;

import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;

@ApplicationScoped
@Traced(operationName = "UserService")
public class UserService {

    @Inject
    IUserMapper userMapper;

    @Inject
    @RestClient
    RestClientKey restClientKeycloak;

    @Inject
    AuthService auth;

    public List<ListUserDto> findAll() {

        List<User> userList = User.listAll();

        return userList.stream()
                       .map(p -> userMapper.toListUserDto(p))
                       .collect(Collectors.toList());

    }

    @Transactional()
    public User persistUser(CreateUserDto dto) {

        User newUser = userMapper.createUserDtoToUser(dto);

        newUser.persistAndFlush();
        
        this.saveUserKeycloak(newUser);

        return newUser;

    }

    @Transactional()
    public User updateUser(Integer idUser, UpdateUserDto dto) throws FrostException {

        User updatedUser = User.findById(idUser);

        if (updatedUser == null) {
            throw new FrostException(EnumErrorCode.USUARIO_NAO_ENCONTRADO);
        }

        updatedUser.setName(dto.getName() == null ? updatedUser.getName()
                : EncryptUtil.textEncrypt(dto.getName(), updatedUser.getSecret()));
        updatedUser.setEmail(dto.getEmail() == null ? updatedUser.getEmail()
                : EncryptUtil.textEncrypt(dto.getEmail(), updatedUser.getSecret()));
        updatedUser.setPhone(dto.getPhone() == null ? updatedUser.getPhone()
                : EncryptUtil.textEncrypt(dto.getPhone(), updatedUser.getSecret()));
        updatedUser.setDocument(dto.getDocument() == null ? updatedUser.getDocument()
                : EncryptUtil.textEncrypt(dto.getDocument(), updatedUser.getSecret()));
        updatedUser.setPassword(dto.getPassword() == null ? updatedUser.getPassword()
                : EncryptUtil.textEncrypt(dto.getPassword(), updatedUser.getSecret()));
        updatedUser.setActive(dto.isActive() == updatedUser.isActive()  ? 
                updatedUser.isActive() : dto.isActive());
        updatedUser.setAcceptTerms(dto.isAcceptTerms() == updatedUser.isAcceptTerms() ? 
                updatedUser.isAcceptTerms() : dto.isAcceptTerms());

        return updatedUser;
        
    }

    @Transactional()
    public User deleteUser(Integer idUser, SecurityIdentity identity) {
        
        // Optional<User> userToDelete = User.findByIdOptional(idUser);

        // userToDelete.ifPresentOrElse(User::delete, () -> {
        //     throw new FrostException(EnumErrorCode.USUARIO_NAO_ENCONTRADO);
        // });

        this.removeUserKeycloak(identity);

        return this.defaultUserToPropagate(idUser, null);

    }

    @Transactional()
    public Address createAddress(Integer idUser, CreateAddressDto dto) {

        User existsUser = User.findById(idUser);

        if (existsUser == null) {
            throw new FrostException(EnumErrorCode.USUARIO_NAO_ENCONTRADO);
        }
        
        var createAddress = userMapper.addressDtoToEntity(dto);

        createAddress.setMain(false);
        createAddress.setUser(existsUser);
        
        createAddress.persist();

        return createAddress;

    }

    @Transactional()
    public Address updateAddress(Integer idUser, Integer idAddress, UpdateAddressDto dto) {

        User existsUser = User.findById(idUser);

        if (existsUser == null) {
            throw new FrostException(EnumErrorCode.USUARIO_NAO_ENCONTRADO);
        }

        var updatedAddress = Address.findByUserIdAddress(existsUser, idAddress);

        if (updatedAddress == null) {
            throw new FrostException(EnumErrorCode.ENDERECO_NAO_ENCONTRADO);
        }

        updatedAddress.setState(dto.getState() == null ? updatedAddress.getState() : dto.getState());
        updatedAddress.setCity(dto.getCity() == null ? updatedAddress.getCity() : dto.getCity());
        updatedAddress.setNumber(dto.getNumber() == null ? updatedAddress.getNumber() : dto.getNumber());
        updatedAddress.setNumberAp(dto.getNumberAp() == null ? updatedAddress.getNumberAp() : dto.getNumberAp());
        updatedAddress.setMain(dto.isMain() == updatedAddress.isMain() ? updatedAddress.isMain() : dto.isMain());
        updatedAddress.setDistrict(dto.getDistrict() == null ? updatedAddress.getDistrict()
                : EncryptUtil.textEncrypt(dto.getDistrict(), updatedAddress.getSecret()));
        updatedAddress.setLatitude(dto.getLatitude() == null ? updatedAddress.getLatitude()
                : EncryptUtil.textEncrypt(dto.getLatitude(), updatedAddress.getSecret()));
        updatedAddress.setLatitude(dto.getLatitude() == null ? updatedAddress.getLatitude()
                : EncryptUtil.textEncrypt(dto.getLatitude(), updatedAddress.getSecret()));
        updatedAddress.setStreet(dto.getStreet() == null ? updatedAddress.getStreet()
                : EncryptUtil.textEncrypt(dto.getStreet(), updatedAddress.getSecret()));

        return updatedAddress;

    }   

    @Transactional()
    public User deleteAddress(Integer idUser, Integer idAddress) {

        User existsUser = User.findById(idUser);

        if (existsUser == null) {
            throw new FrostException(EnumErrorCode.USUARIO_NAO_ENCONTRADO);
        }

        var addressToDelete = Address.findByUserIdAddress(existsUser, idAddress);

        if (addressToDelete == null) {
            throw new FrostException(EnumErrorCode.ENDERECO_NAO_ENCONTRADO);
        }
        
        if (existsUser.getAddress().size() <= 1) {
            throw new FrostException(EnumErrorCode.ERRO_AO_DELETAR_ENDERECO);
        }                                    

        existsUser.getAddress().remove(addressToDelete);

        addressToDelete.delete();

        return this.defaultUserToPropagate(idUser, addressToDelete);

    }

    public void saveUserKeycloak(User newUser) {

        var newUserKeycloak = new CreateUserKeycloakDto();
        var newCredencial = new CreateUserKeycloakCredentialsDto();
        var credencialList = new ArrayList<CreateUserKeycloakCredentialsDto>();

        newUserKeycloak.setUsername(EncryptUtil.textDecrypt(newUser.getEmail(), newUser.getSecret()));
        newUserKeycloak.setEnabled(true);

        newCredencial.setTemporary(false);
        newCredencial.setValue(EncryptUtil.textDecrypt(newUser.getPassword(), newUser.getSecret()));

        credencialList.add(newCredencial);
        
        newUserKeycloak.setCredentials(credencialList);
        newUserKeycloak.setAttributes(Map.of("userId",newUser.getId()));

        var tokenKey = auth.getNewToken();
        var response = restClientKeycloak.createUserKeycloak(tokenKey, newUserKeycloak);

        if (response.getStatus() != 201) {
            throw new FrostException(EnumErrorCode.ERRO_AO_CADASTRAR_USUARIO);
        }

    }

    public void removeUserKeycloak(SecurityIdentity identity) {

        var tokenInfo = (OidcJwtCallerPrincipal) identity.getPrincipal();
        var userIdKeycloak = (String) tokenInfo.getClaim("sub");

        var tokenKey = auth.getNewToken();
        var response = restClientKeycloak.removeUserKeycloak(tokenKey, userIdKeycloak);

        if (response.getStatus() != 204) {
            throw new FrostException(EnumErrorCode.ERRO_AO_DELETAR_USUARIO);
        }

    }

    public User defaultUserToPropagate(Integer id, Address address) {
        
        var user = new User();

        user.setId(id);
        user.setAddress(null);
        user.setDocument("");
        user.setEmail("");
        user.setName("");
        user.setPassword("");
        user.setPhone("");
        user.setSecret("");
        user.setAcceptTerms(false);
        user.setActive(false);

        if (address != null) {

            var listToPropagate = new ArrayList<Address>();

            address.setUser(null);
            listToPropagate.add(address);
    
            user.setAddress(listToPropagate);

        }

        return user;

    }

    public Integer resolveUserId(SecurityIdentity identity) {
        var tokenInfo = (OidcJwtCallerPrincipal) identity.getPrincipal();
        var userId = (JsonNumber) tokenInfo.getClaim("userId");

        return userId.intValue();
    }

}
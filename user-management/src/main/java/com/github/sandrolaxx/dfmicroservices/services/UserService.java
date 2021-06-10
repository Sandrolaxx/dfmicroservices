package com.github.sandrolaxx.dfmicroservices.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.github.sandrolaxx.dfmicroservices.dto.CreateUserDto;
import com.github.sandrolaxx.dfmicroservices.dto.ListUserDto;
import com.github.sandrolaxx.dfmicroservices.dto.UpdateAddressDto;
import com.github.sandrolaxx.dfmicroservices.dto.UpdateUserDto;
import com.github.sandrolaxx.dfmicroservices.entities.Address;
import com.github.sandrolaxx.dfmicroservices.entities.User;
import com.github.sandrolaxx.dfmicroservices.mapper.IUserMapper;

import org.eclipse.microprofile.opentracing.Traced;

@ApplicationScoped
@Traced(operationName = "UserService")
public class UserService {

    @Inject
    IUserMapper userMapper;

    public List<ListUserDto> findAll() {

        List<User> userList = User.listAll();

        return userList.stream().map(p -> userMapper.toListUserDto(p)).collect(Collectors.toList());

    }

    @Transactional()
    public User persistUser(CreateUserDto dto) {

        User newUser = userMapper.createUserDtoToUser(dto);

        newUser.persist();

        return newUser;

    }

    @Transactional()
    public void updateUser(Integer idUser, UpdateUserDto dto) {

        Optional<User> existsUser = User.findByIdOptional(idUser);

        if (!existsUser.isPresent()) {
            throw new NotFoundException();
        }

        var oldUser = existsUser.get();
        var updatedUser = existsUser.get();

        updatedUser.setName(dto.getName() == null ? oldUser.getName() : dto.getName());
        updatedUser.setEmail(dto.getEmail() == null ? oldUser.getEmail() : dto.getEmail());
        updatedUser.setPhone(dto.getPhone() == null ? oldUser.getPhone() : dto.getPhone());
        updatedUser.setDocument(dto.getDocument() == null ? oldUser.getDocument() : dto.getDocument());
        updatedUser.setPassword(dto.getPassword() == null ? oldUser.getPassword() : dto.getPassword());
        updatedUser.setActive(dto.isActive() != oldUser.isActive() ? oldUser.isActive() : dto.isActive());
        updatedUser.setAcceptTerms(dto.isAcceptTerms() != oldUser.isAcceptTerms() ? 
                                        oldUser.isAcceptTerms() : dto.isAcceptTerms());

        updatedUser.persist();

    }

    @Transactional()
    public void deleteUser(Integer idUser) {
        
        Optional<User> userToDelete = User.findByIdOptional(idUser);

        userToDelete.ifPresentOrElse(User::delete, () -> {
            throw new NotFoundException();
        });
        
    }

    @Transactional()
    public void updateAddress(Integer idUser, Integer idAddres, UpdateAddressDto dto) {

        Optional<User> existsUser = User.findByIdOptional(idUser);

        if (!existsUser.isPresent()) {
            throw new NotFoundException();
        }

        var listUserAddress = Address.findByUser(existsUser.get());
        var isExistsAddress = listUserAddress.stream().anyMatch(a -> a.getId().equals(idAddres));

        if (listUserAddress == null 
                || listUserAddress.isEmpty()
                || !isExistsAddress) {
            throw new NotFoundException();
        }

        var oldAddress = listUserAddress.stream()
                                        .filter(a -> a.getId().equals(idAddres))
                                        .findFirst()
                                        .get();

        var updatedAddress = listUserAddress.stream()
                                            .filter(a -> a.getId().equals(idAddres))
                                            .findFirst()
                                            .get();

        updatedAddress.setState(dto.getState() == null ? oldAddress.getState() : dto.getState());
        updatedAddress.setCity(dto.getCity() == null ? oldAddress.getCity() : dto.getCity());
        updatedAddress.setDistrict(dto.getDistrict() == null ? oldAddress.getDistrict() : dto.getDistrict());
        updatedAddress.setStreet(dto.getStreet() == null ? oldAddress.getStreet() : dto.getStreet());
        updatedAddress.setNumber(dto.getNumber() == null ? oldAddress.getNumber() : dto.getNumber());
        updatedAddress.setNumberAp(dto.getNumberAp() == null ? oldAddress.getNumberAp() : dto.getNumberAp());
        updatedAddress.setLatitude(dto.getLatitude() == null ? oldAddress.getLatitude() : dto.getLatitude());
        updatedAddress.setLatitude(dto.getLongitude() == null ? oldAddress.getLongitude() : dto.getLongitude());
        updatedAddress.setMain(dto.isMain() != oldAddress.isMain() ? oldAddress.isMain() : dto.isMain());

        updatedAddress.persist();

    }

    @Transactional()
    public void deleteAddress(Integer idUser, Integer idAddres) {
        
        Optional<User> existsUser = User.findByIdOptional(idUser);

        if (!existsUser.isPresent()) {
            throw new NotFoundException();
        }

        var listUserAddress = Address.findByUser(existsUser.get());
        var isExistsAddress = listUserAddress.stream().anyMatch(a -> a.getId().equals(idAddres));

        if (listUserAddress == null 
                || listUserAddress.isEmpty()
                || !isExistsAddress) {
            throw new NotFoundException();
        }

        var addressToDelete = listUserAddress.stream()
                                        .filter(a -> a.getId().equals(idAddres))
                                        .findFirst()
                                        .get();
                                        
        addressToDelete.delete();

    }
}
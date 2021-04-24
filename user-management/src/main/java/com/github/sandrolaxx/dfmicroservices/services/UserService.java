package com.github.sandrolaxx.dfmicroservices.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import com.github.sandrolaxx.dfmicroservices.dto.CreateUserDto;
import com.github.sandrolaxx.dfmicroservices.dto.ListUserDto;
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

    return userList.stream()
                      .map(p -> userMapper.toListUserDto(p))
                      .collect(Collectors.toList());
  }

  @Transactional()
  public User persistUser(CreateUserDto dto) {
    User newUser = userMapper.createUserDtoToUser(dto);

    newUser.persist();

    return newUser;
  }

  @Transactional()
  public void updateUser(Integer id,@Valid CreateUserDto dto) {
    Optional<User> oldUser = User.findByIdOptional(id);

    if (!oldUser.isPresent()) {
      throw new NotFoundException();
    }

    var updateUserInfo = oldUser.get();
    updateUserInfo.name = dto.name;
    updateUserInfo.email = dto.email;
    updateUserInfo.password = dto.password;
    updateUserInfo.document = dto.document;
    updateUserInfo.phone = dto.phone;

    updateUserInfo.persist();
  }

  @Transactional()
  public void deleteUser(Integer id) {
    Optional<User> productToDelete = User.findByIdOptional(id);

    productToDelete.ifPresentOrElse(User::delete, () -> {
      throw new NotFoundException();
    });
  }
}  
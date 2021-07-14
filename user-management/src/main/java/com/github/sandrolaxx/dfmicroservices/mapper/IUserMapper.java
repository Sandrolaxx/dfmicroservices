package com.github.sandrolaxx.dfmicroservices.mapper;

import java.util.Arrays;

import com.github.sandrolaxx.dfmicroservices.dto.CreateAddressDto;
import com.github.sandrolaxx.dfmicroservices.dto.CreateUserDto;
import com.github.sandrolaxx.dfmicroservices.dto.ListUserDto;
import com.github.sandrolaxx.dfmicroservices.entities.Address;
import com.github.sandrolaxx.dfmicroservices.entities.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", imports = {Arrays.class})
public interface IUserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "secret", ignore = true)
    public User listUserDtoToUser(ListUserDto dto);

    @Mapping(target = "address", expression = "java(Arrays.asList(this.addressDtoToEntity(dto.getAddress())))")
    public User createUserDtoToUser(CreateUserDto dto);

    @Mapping(target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "updatedAt", dateFormat = "dd/MM/yyyy HH:mm:ss")
    public ListUserDto toListUserDto(User user);

    public Address addressDtoToEntity(CreateAddressDto dto); 

}

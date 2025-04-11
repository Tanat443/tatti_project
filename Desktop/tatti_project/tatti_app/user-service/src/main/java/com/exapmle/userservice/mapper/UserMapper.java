package com.exapmle.userservice.mapper;

import com.exapmle.userservice.dto.UserDTO;
import com.exapmle.userservice.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDTO toDto(User user);

    User toModel(UserDTO userDto);

    List<UserDTO> toDtoList(List<User> userList);
}

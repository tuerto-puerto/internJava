package com.example.demo.mappers;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.authorization.AuthRegistrationDTO;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User userDtoToUser(UserDTO dto);
    User authRegistrationDtoToUserEntity(AuthRegistrationDTO dto);
    UserDTO userToUserDto(User user);
}

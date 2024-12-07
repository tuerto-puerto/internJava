package com.example.demo.service;


import com.example.demo.dto.UserDTO;
import com.example.demo.dto.authorization.AuthRegistrationDTO;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    UserDTO register(AuthRegistrationDTO authRegistrationDTO);
    User getCurrentUser();
    void deleteUser();
    void deleteUserById(Long id);
    Optional<UserDTO> findUserByID(Long id);
    List<UserDTO> getAllUsers();
}

package com.example.demo.service;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public JwtResponse register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalStateException("Email is already taken!");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);

        String accessToken = jwtUtil.generateToken(user.getUsername(), true);
        String refreshToken = jwtUtil.generateToken(user.getUsername(), false);

        return new JwtResponse(accessToken, refreshToken);
    }

    public void login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalStateException("User not found");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("Invalid password");
        }
        }
}

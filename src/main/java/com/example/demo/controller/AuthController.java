package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody UserDTO userDTO) {
        try {
            JwtResponse jwtResponse = userService.register(userDTO);
            return ResponseEntity.ok(jwtResponse);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new JwtResponse(e.getMessage(), null));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Проверка и получение сообщения о логине
            userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok("You have logged in");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Authorization error: " + e.getMessage());
        }
    }
}

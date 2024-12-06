package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody UserDTO userDTO) {
        try {
            JwtResponse jwtResponse = userService.register(userDTO);
            return ResponseEntity.ok(jwtResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new JwtResponse(e.getMessage(), null));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new JwtResponse(e.getMessage(), null));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String token) {
        try {
            String accessToken = token.replace("Bearer ", "");

            String username = jwtUtil.validateTokenAndGetUsername(accessToken);
            if (username != null) {
                return ResponseEntity.ok("You have logged in");
            } else {
                return ResponseEntity.status(401).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}

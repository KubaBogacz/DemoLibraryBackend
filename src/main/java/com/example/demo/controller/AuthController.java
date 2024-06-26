package com.example.demo.controller;

import com.example.demo.controller.dto.LoginDto;
import com.example.demo.controller.dto.LoginResponseDto;
import com.example.demo.controller.dto.RegisterDto;
import com.example.demo.controller.dto.RegisterResponseDto;
import com.example.demo.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }
    private final AuthService authService;

    @PostMapping("/register")
//    @PreAuthorize("hasRole('ADMIN')") //there is problem with version of spring boot; line do not work
    public ResponseEntity<RegisterResponseDto> register(@Validated @RequestBody RegisterDto requestBody) {
        RegisterResponseDto dto = authService.register(requestBody);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto requestBody) {
        LoginResponseDto dto = authService.login(requestBody);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}

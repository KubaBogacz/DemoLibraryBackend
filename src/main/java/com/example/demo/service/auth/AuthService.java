package com.example.demo.service.auth;

import com.example.demo.controller.dto.LoginDto;
import com.example.demo.controller.dto.LoginResponseDto;
import com.example.demo.controller.dto.RegisterDto;
import com.example.demo.controller.dto.RegisterResponseDto;
import com.example.demo.infrastructure.entity.AuthEntity;
import com.example.demo.infrastructure.entity.UserEntity;
import com.example.demo.infrastructure.repository.AuthRepository;
import com.example.demo.infrastructure.repository.UserRepository;
import com.example.demo.service.auth.error.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthRepository authRepository, UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponseDto register(RegisterDto dto){
        Optional<AuthEntity> existingAuth = authRepository.findByUsername(dto.getUsername());

        if(existingAuth.isPresent()){
            throw UserAlreadyExistsException.create(dto.getUsername());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(dto.getEmail());
        userRepository.save(userEntity);

        AuthEntity authEntity = new AuthEntity();
        authEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        authEntity.setUsername(dto.getUsername());
        authEntity.setRole(dto.getRole());
        authEntity.setUser(userEntity);

        authRepository.save(authEntity);

        return new RegisterResponseDto(userEntity.getId(), authEntity.getUsername(), authEntity.getRole());
    }

    public LoginResponseDto login(LoginDto dto){
        AuthEntity authEntity = authRepository.findByUsername(dto.getUsername()).orElseThrow(RuntimeException::new);

        if(!passwordEncoder.matches(dto.getPassword(), authEntity.getPassword())){
            throw new RuntimeException();
        }

        String token = jwtService.generateToken(authEntity);

        return new LoginResponseDto(token);
    }
}

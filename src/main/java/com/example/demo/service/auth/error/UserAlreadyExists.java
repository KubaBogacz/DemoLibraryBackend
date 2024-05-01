package com.example.demo.service.auth.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExists {
    public static ResponseStatusException create(String username) {
        return new ResponseStatusException(HttpStatus.CONFLICT, String.format("User '%s' already exists", username));
    }
}

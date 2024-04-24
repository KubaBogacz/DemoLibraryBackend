package com.example.demo.service.auth.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends RuntimeException {
    private UserAlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String username) {
        UserAlreadyExistsException userAlreadyExistsException = new UserAlreadyExistsException(String.format("User '%s' already exists", username));
        return new ResponseStatusException(HttpStatus.CONFLICT, userAlreadyExistsException.getMessage(), userAlreadyExistsException);
    }
}

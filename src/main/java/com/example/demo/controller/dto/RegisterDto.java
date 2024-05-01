package com.example.demo.controller.dto;

import com.example.demo.commonTypes.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterDto {
    @NotBlank(message = "Password may not be blank")
    private String password;
    @NotBlank(message = "Username may not be blank")
    private String username;
    @NotNull
    private UserRole role;
    @NotBlank(message = "Email may not be blank")
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

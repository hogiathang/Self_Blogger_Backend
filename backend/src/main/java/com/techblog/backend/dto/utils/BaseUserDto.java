package com.techblog.backend.dto.utils;

import java.time.LocalDateTime;

public class BaseUserDto {
    private final String username;
    private final String password;
    private final LocalDateTime createdAt;

    public BaseUserDto(String username, String password) {
        this.username = username;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

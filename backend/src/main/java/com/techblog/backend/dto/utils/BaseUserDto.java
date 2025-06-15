package com.techblog.backend.dto.utils;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Base User Data Transfer Object")
public class BaseUserDto {
    @Schema(
            description = "Username of the user",
            example = "john_doe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private final String username;

    @Schema(
            description = "Password of the user",
            example = "securePassword123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private final String password;

    @Schema(
            description = "Creation timestamp of the user",
            example = "2023-10-01T12:00:00",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
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

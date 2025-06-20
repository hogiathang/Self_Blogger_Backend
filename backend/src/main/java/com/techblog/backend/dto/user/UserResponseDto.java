package com.techblog.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String username;
    private LocalDateTime dateCreated;
    private String avatarUrl;
    private boolean isActive;
}

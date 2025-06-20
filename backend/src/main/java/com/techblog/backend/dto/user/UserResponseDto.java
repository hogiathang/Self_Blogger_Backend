package com.techblog.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    public Long userId;
    public String username;
    public LocalDateTime dateCreated;
    public boolean isActive;
}

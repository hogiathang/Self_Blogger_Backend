package com.techblog.backend.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSecureResponse {
    private Long userId;

    private String username;

    private String email;

    private String phone;

    private LocalDateTime dateCreated;

    private String role;

    private boolean isActive;
}

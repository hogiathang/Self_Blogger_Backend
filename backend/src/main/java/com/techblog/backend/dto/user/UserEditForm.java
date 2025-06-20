package com.techblog.backend.dto.user;

import lombok.Data;

@Data
public class UserEditForm {
    private String email;
    private String phoneNumber;
    private boolean active;
}

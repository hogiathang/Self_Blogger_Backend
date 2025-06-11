package com.techblog.backend.dto;

import com.techblog.backend.dto.utils.BaseUserDto;

public class LoginForm extends BaseUserDto {

    public LoginForm(String username, String password) {
        super(username, password);
    }
}

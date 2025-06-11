package com.techblog.backend.dto;

import com.techblog.backend.dto.utils.BaseUserDto;

public class RegisterForm extends BaseUserDto {

    public String ref;

    public RegisterForm(String username, String password, String ref) {
        super(username, password);
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }
}

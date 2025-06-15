package com.techblog.backend.dto;

import com.techblog.backend.dto.utils.BaseUserDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Đối tượng dùng để đăng kí người dùng mới",
        title = "RegisterForm"
)
public class RegisterForm extends BaseUserDto {

    @Schema(
            description = "Mã giới thiệu của người dùng",
            example = "ref123"
    )
    public String ref;

    public RegisterForm(String username, String password, String ref) {
        super(username, password);
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }
}

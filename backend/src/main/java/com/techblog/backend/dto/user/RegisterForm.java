package com.techblog.backend.dto.user;

import com.techblog.backend.dto.utils.BaseUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
        description = "Đối tượng dùng để đăng kí người dùng mới",
        title = "RegisterForm"
)
public class RegisterForm extends BaseUserDto {

    @Schema(
            description = "Mã giới thiệu của người dùng",
            example = "ref123"
    )
    public String email;

    public String phone;

    public RegisterForm(String username, String password, String email, String phone) {
        super(username, password);
        this.email = email;
        this.phone = phone;
    }

}

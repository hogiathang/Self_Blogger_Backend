package com.techblog.backend.service.publicInterface.user;

import com.techblog.backend.dto.user.RegisterForm;
import com.techblog.backend.dto.user.UserResponseDto;

public interface IUserPublicService {
    UserResponseDto addUser(RegisterForm user);
    UserResponseDto authenticate(String username, String rawPassword);
}

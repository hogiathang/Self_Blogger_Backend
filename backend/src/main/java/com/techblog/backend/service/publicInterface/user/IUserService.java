package com.techblog.backend.service.publicInterface.user;

import com.techblog.backend.dto.user.UserEditForm;
import com.techblog.backend.dto.user.UserResponseDto;
import com.techblog.backend.dto.user.UserSecureResponse;

public interface IUserService {
    UserSecureResponse getUserInfo(String username);
    UserResponseDto editUser(String username, UserEditForm user);
    void deleteUser(String username);
}

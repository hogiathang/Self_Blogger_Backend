package com.techblog.backend.service.publicInterface.user;

import com.techblog.backend.dto.user.UserEditForm;
import com.techblog.backend.dto.user.UserResponseDto;
import com.techblog.backend.dto.user.UserSecureResponse;

public interface IUserService {
    UserSecureResponse getUserInfo(Long id, String username);
    UserResponseDto editUser(Long id, String username, UserEditForm user);
    void deleteUser(Long id);
}

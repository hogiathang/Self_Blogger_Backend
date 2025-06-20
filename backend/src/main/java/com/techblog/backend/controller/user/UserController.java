package com.techblog.backend.controller.user;

import com.techblog.backend.dto.user.UserEditForm;
import com.techblog.backend.dto.user.UserResponseDto;
import com.techblog.backend.dto.user.UserSecureResponse;
import com.techblog.backend.service.publicInterface.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Chỉnh sửa thông tin người dùng
     */

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDto> editUser(
            @PathVariable Long userId,
            @RequestBody UserEditForm userEditForm
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserResponseDto updatedUser = userService.editUser(userId, auth.getName(), userEditForm);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    /**
     * Lấy thông tin người dùng
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserSecureResponse> getUserInfo(@PathVariable Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserSecureResponse userInfo = userService.getUserInfo(userId, auth.getName());
        return ResponseEntity.status(HttpStatus.OK).body(userInfo);
    }

    /**
     * Xoá người dùng
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
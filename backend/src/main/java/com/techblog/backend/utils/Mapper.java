package com.techblog.backend.utils;

import com.techblog.backend.dto.blog.BlogRequestDto;
import com.techblog.backend.dto.user.RegisterForm;
import com.techblog.backend.dto.user.UserEditForm;
import com.techblog.backend.dto.user.UserResponseDto;
import com.techblog.backend.dto.user.UserSecureResponse;
import com.techblog.backend.entity.blog.BlogEntity;
import com.techblog.backend.entity.user.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Mapper {
    /**
     * Chuyển đổi các thuộc tính từ UserEditForm sang UserEntity.
     * @param userEditForm Form chỉnh sửa người dùng
     * @param userEntity Thực thể người dùng hiện tại
     * @return UserEntity đã được cập nhật với các giá trị từ UserEditForm
     */
    public static UserEntity editForm2UserEntity(UserEditForm userEditForm, UserEntity userEntity) {
        userEntity.setEmail((String) Utils.setValue(userEntity.getEmail(), userEditForm.getEmail()));
        userEntity.setPhone((String) Utils.setValue(userEntity.getPhone(), userEditForm.getPhoneNumber()));
        userEntity.setAvatarUrl((String) Utils.setValue(userEntity.getAvatarUrl(), userEditForm.getAvatarUrl()));
        userEntity.setActive(userEditForm.isActive());
        return userEntity;
    }

    /**
     * Chuyển đổi UserEntity sang UserResponseDto.
     * @param user Thực thể người dùng
     * @return UserResponseDto chứa thông tin người dùng
     */
    public static UserResponseDto userEntity2UserResponseDto(UserEntity user) {
        return new UserResponseDto(
                user.getUsername(),
                user.getDateCreated(),
                user.getAvatarUrl(),
                user.isActive()
        );
    }

    /**
     * Chuyển đổi UserEntity sang UserSecureResponse.
     * @param user Thực thể người dùng
     * @return UserSecureResponse chứa thông tin bảo mật của người dùng
     */
    public static UserSecureResponse userEntity2UserSecureResponse(UserEntity user) {
        return new UserSecureResponse(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getDateCreated(),
                user.getAvatarUrl(),
                user.getRole(),
                user.isActive()
        );
    }

    /**
     * Chuyển đổi RegisterForm sang UserEntity.
     * @param registerForm Form đăng ký người dùng
     * @param passwordEncoder Mã hóa mật khẩu
     * @param role Vai trò của người dùng
     * @return UserEntity đã được tạo từ RegisterForm
     */
    public static UserEntity registerForm2UserEntity(RegisterForm registerForm,
                                                     BCryptPasswordEncoder passwordEncoder,
                                                     String role) {
        return new UserEntity(
                registerForm.getUsername(),
                passwordEncoder.encode(registerForm.getPassword()),
                registerForm.getEmail(),
                registerForm.getPhone(),
                role
        );
    }


    public static BlogEntity blogRequest2BlogEntity(BlogRequestDto blogRequest, BlogEntity blog) {
        blog.setTitle(blogRequest.getTitle());
        blog.setDescription(blogRequest.getDescription());
        blog.setTags(
                blogRequest.getTags()
                        .stream()
                        .reduce(
                                "",
                                (tag1, tag2) -> tag1 + (tag1.isEmpty() ? "" : ",") + tag2
                        )
        );
        blog.setId(blogRequest.getBlogId());
        blog.setHtmlPath(blogRequest.getBlogUrl());
        blog.setContentSize(blogRequest.getContentSize());
        blog.setContentType(blogRequest.getContentType());
        return blog;
    }
}

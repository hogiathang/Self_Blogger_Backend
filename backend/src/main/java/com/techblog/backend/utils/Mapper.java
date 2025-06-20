package com.techblog.backend.utils;

import com.techblog.backend.dto.user.UserEditForm;
import com.techblog.backend.entity.user.UserEntity;

public class Mapper {
    /**
     * Chuyển đổi các thuộc tính từ UserEditForm sang UserEntity.
     * @param userEditForm Form chỉnh sửa người dùng
     * @param userEntity Thực thể người dùng hiện tại
     * @return UserEntity đã được cập nhật với các giá trị từ UserEditForm
     */
    public static UserEntity editForm2UserEntity(UserEditForm userEditForm, UserEntity userEntity) {
        userEntity.setUsername((String) Utils.setValue(userEntity.getUsername(), userEditForm.getUsername()));
        userEntity.setEmail((String) Utils.setValue(userEntity.getEmail(), userEditForm.getEmail()));
        userEntity.setPhone((String) Utils.setValue(userEntity.getPhone(), userEditForm.getPhoneNumber()));
        userEntity.setActive(userEditForm.isActive());
        return userEntity;
    }
}

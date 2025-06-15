package com.techblog.backend.service.publicInterface;

import com.techblog.backend.entity.user.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> getAllUsers();

    UserEntity getUserById(Long id);

    void addUser(UserEntity user);

    UserEntity updateUser(Long id, UserEntity user);

    void deleteUser(Long id);

    boolean authenticate(String username, String rawPassword);
}

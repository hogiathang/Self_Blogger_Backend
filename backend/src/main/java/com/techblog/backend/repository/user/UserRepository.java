package com.techblog.backend.repository.user;

import com.techblog.backend.entity.user.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users(username, password, email, phone, role, avatar_url)" +
            " VALUES (:username, :password, :email, :phone, :role, :avatarUrl)", nativeQuery = true)
    void saveUser(
            String username,
            String password,
            String email,
            String phone,
            String role,
            String avatarUrl
    );
}

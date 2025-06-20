package com.techblog.backend.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id", nullable = false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = true, unique = true)
    private String email;

    @Column(name = "phone", nullable = true, unique = true)
    private String phone;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "role")
    private String role;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public UserEntity() {
        this.dateCreated = LocalDateTime.now();
    }

    public UserEntity(String username, String password, String email, String phone, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.dateCreated = LocalDateTime.now();
        this.isActive = true;
    }
}

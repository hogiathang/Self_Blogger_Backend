package com.techblog.backend.entity.user;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id", nullable = false)
    @Getter
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    @Getter
    private String username;

    @Column(name = "password", nullable = false)
    @Getter
    private String password;

    @Column(name = "ref", nullable = false, unique = true)
    @Getter
    private String ref;

    @Column(name = "date_created", nullable = false)
    @Getter
    private String dateCreated;

    @Column(name = "role")
    @Getter
    private String role;

    public UserEntity() {
        this.dateCreated = LocalDateTime.now().toString();
    }

    public UserEntity(String username, String password, String ref, String role) {
        this.username = username;
        this.password = password;
        this.ref = ref;
        this.role = role;
        this.dateCreated = LocalDateTime.now().toString();
    }
}

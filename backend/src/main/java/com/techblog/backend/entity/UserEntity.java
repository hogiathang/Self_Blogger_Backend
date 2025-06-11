package com.techblog.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


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

    @Column(name = "ref", nullable = false, unique = true)
    private String ref;

    @Column(name = "date_created", nullable = false)
    private String dateCreated;

    @Column(name = "role")
    private String role;

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }

    public String ref() {
        return this.ref;
    }

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

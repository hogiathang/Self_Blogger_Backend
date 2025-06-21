package com.techblog.backend.entity.user;

import com.techblog.backend.entity.BaseEntity;
import com.techblog.backend.entity.blog.BlogEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
@Entity(name = "users")
public class UserEntity extends BaseEntity implements Serializable {
    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "role")
    private String role;

    @Column(name = "avatar_url", nullable = false)
    private String avatarUrl;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<BlogEntity> blogs;

    public UserEntity() {
        this.avatarUrl   = "http://localhost:8081/api/v1/images/default/avatar.png";
    }

    public UserEntity(String username, String password, String email, String phone, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.avatarUrl = "http://localhost:8081/api/v1/images/default/avatar.png";
    }

    public void setActive(boolean isActive) {
        super.setActive(isActive);
    }

    public LocalDateTime getDateCreated() {
        return super.getCreatedAt();
    }
}

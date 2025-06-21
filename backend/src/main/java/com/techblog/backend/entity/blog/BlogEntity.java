package com.techblog.backend.entity.blog;

import com.techblog.backend.entity.BaseEntity;
import com.techblog.backend.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@Table(name = "blog")
@AllArgsConstructor
@NoArgsConstructor
public class BlogEntity extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, unique = false)
    private String title;

    @Column(name = "html_path", nullable = false, unique = true)
    private String htmlPath;

    @Column(name = "tags", nullable = false)
    private String tags;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "content_size", nullable = false)
    private long contentSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private UserEntity author;
}

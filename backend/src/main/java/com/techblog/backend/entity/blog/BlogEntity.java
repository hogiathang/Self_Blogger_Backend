package com.techblog.backend.entity.blog;

import com.techblog.backend.dto.blog.BlogContent;
import com.techblog.backend.utils.BlogContentConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@Table(name = "blog")
@AllArgsConstructor
@NoArgsConstructor
public class BlogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "title", nullable = false, unique = false)
    private String title;

    @Convert(converter = BlogContentConverter.class)
    @Column(name = "description", nullable = false)
    private List<BlogContent> content;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name= "version", nullable = false)
    private int version;
}

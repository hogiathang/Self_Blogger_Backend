package com.techblog.backend.repository;

import com.techblog.backend.entity.blog.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlogRepository extends JpaRepository<BlogEntity, UUID> {
}

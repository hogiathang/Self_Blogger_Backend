package com.techblog.backend.repository;

import com.techblog.backend.entity.blog.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogEntity, String> {
}

package com.techblog.backend.repository;

import com.techblog.backend.entity.blog.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<BlogEntity, String> {
    @Query("SELECT blogs FROM BlogEntity blogs WHERE blogs.title = ?1")
    List<BlogEntity> getListBlogsWithOldVersion(String title);
}

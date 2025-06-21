package com.techblog.backend.repository.blog;

import com.techblog.backend.entity.blog.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BlogRepository extends JpaRepository<BlogEntity, UUID> {
    @Query(
            value = "SELECT * FROM blog WHERE username = :authorName ORDER BY created_at DESC LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<BlogEntity> findByAuthorName(
            @Param("authorName") String authorName,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}

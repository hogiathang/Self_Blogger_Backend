package com.techblog.backend.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {
    private UUID blogId;
    private InputStream contentStream;
    private String contentType;
    private long contentSize;
    private String title;
    private String description;
    private List<String> tags;
    private String thumbnailUrl;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
}

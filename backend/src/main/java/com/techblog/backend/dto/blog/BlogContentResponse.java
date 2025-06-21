package com.techblog.backend.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogContentResponse {
    private String blogId;
    private String title;
    private String content;
    private List<String> tags;
    private String description;
    private String authorName;
    private String authorProfilePictureUrl;
    private LocalDateTime createdAt;
    private boolean isDraft;
}

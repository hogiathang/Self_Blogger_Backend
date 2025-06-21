package com.techblog.backend.dto.blog;

import lombok.Data;

import java.util.List;

@Data
public class BlogPatchRequest {
    private String title;
    private String description;
    private List<String> tags;
    private String authorUsername;
    private String blogUrl;
    private boolean isDraft;
}

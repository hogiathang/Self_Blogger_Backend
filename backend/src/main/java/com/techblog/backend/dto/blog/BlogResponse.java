package com.techblog.backend.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogResponse {
    private String blogId;
    private String blogUrl;
    private String message;
}

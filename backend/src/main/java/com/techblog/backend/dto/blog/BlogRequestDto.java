package com.techblog.backend.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequestDto {
    private String content;
    private String contentType;
    private long contentSize;
    private String title;
    private String description;
    private List<String> tags;
    private String authorUsername;
    private String blogUrl;
    private UUID blogId;
}

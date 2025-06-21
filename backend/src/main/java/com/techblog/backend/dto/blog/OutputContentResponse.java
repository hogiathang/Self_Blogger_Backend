package com.techblog.backend.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputContentResponse {
    private InputStream contentStream;
    private String contentType;
    private long contentSize;
    private String contentName;
    private String contentUrl;
    private String contentId;
    private String authorUsername;
}

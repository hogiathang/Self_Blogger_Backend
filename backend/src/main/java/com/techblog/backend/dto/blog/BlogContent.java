package com.techblog.backend.dto.blog;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogContent {
    private String type;
    private String cssVariables;
    private String content;
}
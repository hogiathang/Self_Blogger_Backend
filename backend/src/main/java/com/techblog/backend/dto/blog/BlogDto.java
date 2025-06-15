package com.techblog.backend.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {
    private String blogTitle;
    private List<BlogContent> blogContent;
}

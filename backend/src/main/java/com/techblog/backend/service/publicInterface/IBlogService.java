package com.techblog.backend.service.publicInterface;


import com.techblog.backend.dto.blog.BlogDto;

public interface IBlogService {
    String postBlog(String username, BlogDto blogDto);

    void deleteBlog(String username, String blogId);

    String updateBlog(String username, String blogId, BlogDto blogDto);
}

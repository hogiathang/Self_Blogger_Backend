package com.techblog.backend.service.publicInterface.file;


import com.techblog.backend.dto.blog.BlogDto;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface IBlogService {
    String postBlog(BlogDto blogRequest, HttpServletRequest request) throws IOException;

    BlogDto getPublishBlog(String blogId);

    BlogDto getAuthBlog(String username, String blogId);

    void deleteBlog(String username, String blogId);

    String updateBlogStatus(String username, String blogId);
}
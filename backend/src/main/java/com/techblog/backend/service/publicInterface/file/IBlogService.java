package com.techblog.backend.service.publicInterface.file;


import com.techblog.backend.dto.blog.BlogRequestDto;
import com.techblog.backend.dto.blog.BlogResponse;
import com.techblog.backend.dto.blog.OutputContentResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;

public interface IBlogService {
    BlogResponse postBlog(BlogRequestDto blogRequest, HttpServletRequest request) throws IOException;

    OutputContentResponse getPublishBlog(String blogId);
//
//    BlogDto getAuthBlog(String username, String blogId);
//
    void deleteBlog(String username, String blogId);
//
//    String updateBlogStatus(String username, String blogId);
}
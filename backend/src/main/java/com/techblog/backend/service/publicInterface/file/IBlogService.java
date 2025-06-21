package com.techblog.backend.service.publicInterface.file;


import com.techblog.backend.dto.blog.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IBlogService {
    BlogResponse postBlog(BlogRequestDto blogRequest, HttpServletRequest request) throws IOException;

    OutputContentResponse getPublishBlog(String blogId);
//
//    BlogDto getAuthBlog(String username, String blogId);
//
    void deleteBlog(String username, String blogId);
//
    BlogResponse updateBlog(String blogId, BlogPatchRequest blogRequest);

    List<BlogContentResponse> getOwnBlogs(String username, int page, int size);
}
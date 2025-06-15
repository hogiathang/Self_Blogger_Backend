package com.techblog.backend.controller;

import com.techblog.backend.dto.blog.BlogDto;
import com.techblog.backend.service.publicInterface.IBlogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/blog")
/**
 * Thực hiện các hành động liên quan đến blog của người dùng:
 * * - Post Blog
 * * - Delete Blog
 * * - Update Blog
 * * - Get User Blog
 */
public class BloggedController {
    private final IBlogService blogService;

    public BloggedController(IBlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * Thêm blog mới
     * @return Trả về thông tin blog đã được thêm
     */
    @PostMapping("/post")
    public ResponseEntity<String> postNewBlog(@RequestBody BlogDto blogDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(blogService.postBlog(auth.getName(), blogDto));
    }

    /**
     * Xóa blog
     * @return Trả về thông tin blog đã được xóa
     */
    @PostMapping("/delete")
    public void deleteBlog() {}

    /**
     * Cập nhật nội dung blog
     * @return Trả về thông tin blog đã được cập nhật
     */
    @PostMapping("/update")
    public void updateBlog() {}

    /**
     * Lấy danh sách blog của người dùng
     * @return Danh sách blog
     */
    @PostMapping("/list")
    public void getListBlog() {}
}
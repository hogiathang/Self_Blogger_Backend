package com.techblog.backend.controller;

import com.techblog.backend.dto.blog.BlogDto;
import com.techblog.backend.service.publicInterface.IBlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("")
    public ResponseEntity<String> postNewBlog(@RequestBody BlogDto blogDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(blogService.postBlog(auth.getName(), blogDto));
    }

    /**
     * Xóa blog
     * @return Trả về thông tin blog đã được xóa
     */
    @PostMapping("/delete/{blogTitle}")
    public ResponseEntity<String> deleteBlog(@PathVariable String blogTitle) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        blogService.deleteBlog(auth.getName(), blogTitle);
        return ResponseEntity.ok().body("Blog deleted successfully");
    }

    /**
     * Cập nhật nội dung blog
     * @return Trả về id cuả blog đã được cập nhập
     */
    @PostMapping("/update/{blogId}")
    public ResponseEntity<String> updateBlog(@PathVariable String blogId, @RequestBody BlogDto blogDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(blogService.updateBlog(auth.getName(), blogId, blogDto));
    }
}
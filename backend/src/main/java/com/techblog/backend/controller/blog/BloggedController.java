package com.techblog.backend.controller.blog;

import com.techblog.backend.dto.blog.BlogDto;
import com.techblog.backend.service.publicInterface.file.IBlogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/blog")
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
     * Tạo 1 bảng nháp cho blog.
     * @return Trả về đường dẫn URL của blog đã được thêm
     */
    @PostMapping("")
    public ResponseEntity<String> postNewBlog(
            @RequestParam("blog") MultipartFile blog,
            @RequestParam("blogRequest") BlogDto blogRequest,
            HttpServletRequest request
    ) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            blogRequest.setAuthorName(auth.getName());
            blogRequest.setBlogId(UUID.randomUUID());

            blogRequest.setContentStream(blog.getInputStream());
            blogRequest.setContentSize(blog.getSize());
            blogRequest.setContentType(blog.getContentType());
            blogRequest.setStatus("draft");

            return ResponseEntity.ok().body(blogService.postBlog(blogRequest, request));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error while posting blog: " + e.getMessage());
        }
    }

    /**
     * Lấy thông tin blog được bảo mật
     */
    @GetMapping("/auth/{blogId}")
    public ResponseEntity<BlogDto> getUserAuthBlog(@PathVariable String blogId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogService.getAuthBlog(auth.getName(), blogId));
    }

    /**
     * Lấy thông tin blog đã được xuất bản
     * @return Trả về thông tin blog đã được xuất bản
     */
    @GetMapping("/{blogId}")
    public ResponseEntity<BlogDto> getPublishedBlog(@PathVariable String blogId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogService.getPublishBlog(blogId));
    }

    @PatchMapping("/{blogId}")
    public ResponseEntity<String> updateBlogStatus(
            @RequestParam("status") String status
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok()
                .body(blogService.updateBlogStatus(auth.getName(), status));
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<String> deleteBlog(@PathVariable String blogId) {
        // TODO: Kiểm tra quyền của người dùng trước khi xóa blog
        // TODO: Hiện thực chức năng xóa Blog
        return null;
    }
}
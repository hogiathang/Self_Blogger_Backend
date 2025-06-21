package com.techblog.backend.controller.blog;

import com.techblog.backend.dto.blog.BlogPatchRequest;
import com.techblog.backend.dto.blog.BlogRequestDto;
import com.techblog.backend.dto.blog.BlogResponse;
import com.techblog.backend.dto.blog.OutputContentResponse;
import com.techblog.backend.service.publicInterface.file.IBlogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    public ResponseEntity<BlogResponse> postNewBlog(
            @RequestBody BlogRequestDto blogRequest,
            HttpServletRequest request
    ) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        blogRequest.setAuthorUsername(auth.getName());
        blogRequest.setContentSize(blogRequest.getContent().getBytes().length);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(blogService.postBlog(blogRequest, request));
    }

    @PatchMapping("/{blogId}")
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable String blogId,
            @RequestBody BlogPatchRequest blogRequest
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        blogRequest.setAuthorUsername(auth.getName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogService.updateBlog(blogId, blogRequest));
    }

    /**
     * Lấy thông tin blog công khai
     * @param blogId ID của blog cần lấy
     * @param response Đối tượng HttpServletResponse để trả về nội dung blog
     * @return Trả về ResponseEntity với mã trạng thái OK
     * @throws IOException Nếu có lỗi khi đọc nội dung blog
     */
    @GetMapping("/{blogId}")
    public ResponseEntity<Void> viewBlog(@PathVariable String blogId,
                                         HttpServletResponse response) throws IOException {
        OutputContentResponse responseContent = blogService.getPublishBlog(blogId);

        response.setContentType(responseContent.getContentType());
        response.setContentLengthLong(responseContent.getContentSize());
        response.setHeader("Content-Disposition", "inline; filename=\"" + responseContent.getContentName() + "\"");
        response.setHeader("Content-ID", responseContent.getContentId());
        response.setHeader("Author-Username", responseContent.getAuthorUsername());
        response.getOutputStream().write(responseContent.getContentStream().readAllBytes());
        response.getOutputStream().flush();
        response.getOutputStream().close();

        return ResponseEntity.status(HttpStatus.OK)
                .body(null);
    }

    /**
     * Xoá blog của người dùng
     * @param blogId ID của blog cần xoá
     * @return Trả về đối tượng BlogResponse với thông báo xoá thành công
     */
    @DeleteMapping("/{blogId}")
    public ResponseEntity<BlogResponse> deleteBlog(@PathVariable String blogId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        blogService.deleteBlog(auth.getName(), blogId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new BlogResponse(blogId,null, "Blog deleted successfully"));
    }

    /**
     * Lấy danh sách blog của người dùng (có phân trang)
     * @param page trang hiện tại (mặc định = 0)
     * @param size số phần tử mỗi trang (mặc định = 10)
     * @return danh sách blog
     */
    @GetMapping("")
    public ResponseEntity<?> getUserBlog(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogService.getOwnBlogs(auth.getName(), page, size));
    }
}
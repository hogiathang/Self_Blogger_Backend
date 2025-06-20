package com.techblog.backend.service.blog;

import com.techblog.backend.dto.blog.BlogDto;
import com.techblog.backend.entity.blog.BlogEntity;
import com.techblog.backend.repository.BlogRepository;
import com.techblog.backend.service.publicInterface.file.FileStorageService;
import com.techblog.backend.service.publicInterface.file.IBlogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class BlogService implements IBlogService {

    private final FileStorageService fileStorageService;

    private final BlogRepository blogRepository;

    public BlogService(FileStorageService fileStorageService,
                       BlogRepository blogRepository) {
        this.fileStorageService = fileStorageService;
        this.blogRepository = blogRepository;
    }

    @Override
    public String postBlog(BlogDto blogRequest, HttpServletRequest request) throws IOException {

        String fileURI = fileStorageService.uploadFile(
                "blog." + blogRequest.getAuthorName().toLowerCase(),
                blogRequest.getBlogId().toString() + ".html",
                blogRequest.getContentStream(),
                blogRequest.getContentSize(),
                blogRequest.getContentType()
        );

        StringBuilder blogUrl = new StringBuilder(request.getScheme());
        blogUrl.append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append("/api/v1/blogs/")
                .append(blogRequest.getBlogId());

        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setId(blogRequest.getBlogId());
        blogEntity.setTitle(blogRequest.getTitle());
        blogEntity.setAuthor(blogRequest.getAuthorName());
        blogEntity.setHtmlPath(fileURI);
        blogEntity.setTags(
                blogRequest.getTags()
                        .stream()
                        .reduce(
                                "",
                                (tag1, tag2) -> tag1 + (tag1.isEmpty() ? "" : ",") + tag2
                        )
        );
        blogEntity.setDescription(blogRequest.getDescription());
        blogEntity.setStatus(blogRequest.getStatus());
        blogRepository.save(blogEntity);
        return blogUrl.toString();
    }

    @Override
    public BlogDto getPublishBlog(String blogId) {
        BlogEntity blogEntity = blogRepository.findById(UUID.fromString(blogId))
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        return createBlogDto(blogEntity);
    }

    @Override
    public BlogDto getAuthBlog(String username, String blogId) {
        BlogEntity blogEntity = blogRepository.findById(UUID.fromString(blogId))
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        if (!blogEntity.getAuthor().equalsIgnoreCase(username)) {
            throw new RuntimeException("You do not have permission to access this blog");
        }
        return createBlogDto(blogEntity);
    }


    @Override
    public void deleteBlog(String username, String blogId) {

    }

    @Override
    public String updateBlogStatus(String username, String blogId) {
        return "blog status updated successfully";
    }

    private BlogDto createBlogDto(BlogEntity blogEntity) {
        InputStream contentStream = fileStorageService.getFile(
                "blog." + blogEntity.getAuthor().toLowerCase(),
                blogEntity.getId().toString() + ".html"

        );

        BlogDto blogDto = new BlogDto();
        blogDto.setBlogId(blogEntity.getId());
        blogDto.setTitle(blogEntity.getTitle());
        blogDto.setAuthorName(blogEntity.getAuthor());
        blogDto.setDescription(blogEntity.getDescription());
        blogDto.setThumbnailUrl(blogEntity.getHtmlPath());
        blogDto.setTags(List.of(blogEntity.getTags().split(",")));
        blogDto.setCreatedAt(blogEntity.getCreatedAt());
        blogDto.setUpdatedAt(blogEntity.getUpdatedAt());
        blogDto.setStatus(blogEntity.getStatus());
        blogDto.setContentStream(contentStream);
        return blogDto;
    }
}

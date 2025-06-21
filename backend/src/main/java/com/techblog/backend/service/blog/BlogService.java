package com.techblog.backend.service.blog;

import com.techblog.backend.dto.blog.BlogRequestDto;
import com.techblog.backend.dto.blog.BlogResponse;
import com.techblog.backend.dto.blog.OutputContentResponse;
import com.techblog.backend.entity.blog.BlogEntity;
import com.techblog.backend.entity.user.UserEntity;
import com.techblog.backend.exception.all.NoContentException;
import com.techblog.backend.repository.blog.BlogRepository;
import com.techblog.backend.repository.user.UserRepository;
import com.techblog.backend.service.publicInterface.file.FileStorageService;
import com.techblog.backend.service.publicInterface.file.IBlogService;
import com.techblog.backend.utils.Mapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class BlogService implements IBlogService {

    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    public BlogService(FileStorageService fileStorageService, UserRepository userRepository,
                       BlogRepository blogRepository) {
        this.fileStorageService = fileStorageService;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
    }

    @Transactional
    @Override
    public BlogResponse postBlog(BlogRequestDto blogRequest, HttpServletRequest request) throws IOException {
        UUID blogId = UUID.randomUUID();

        InputStream contentStream = new ByteArrayInputStream(
                blogRequest.getContent().getBytes(StandardCharsets.UTF_8)
        );

        fileStorageService.uploadFile(
                "blog." + blogRequest.getAuthorUsername().toLowerCase(),
                blogId.toString() + ".html",
                contentStream,
                blogRequest.getContentSize(),
                blogRequest.getContentType()
        );

        StringBuilder blogUrl = new StringBuilder(request.getScheme());
        blogUrl.append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append("/api/v1/blog/")
                .append(blogId.toString());

        blogRequest.setBlogId(blogId);
        blogRequest.setBlogUrl(blogUrl.toString());

        BlogEntity blogEntity = Mapper.blogRequest2BlogEntity(blogRequest, new BlogEntity());
        UserEntity userRef = userRepository.getReferenceById(blogRequest.getAuthorUsername());
        blogEntity.setAuthor(userRef);

        blogRepository.save(blogEntity);
        return new BlogResponse(
                blogEntity.getId().toString(),
                blogEntity.getHtmlPath(),
                "Blog created successfully"
        );
    }

    @Override
    public OutputContentResponse getPublishBlog(String blogId) {
        BlogEntity blogEntity = blogRepository.findById(UUID.fromString(blogId))
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        InputStream contentStream = fileStorageService.getFile(
                "blog." + blogEntity.getAuthor().getUsername().toLowerCase(),
                blogEntity.getId().toString() + ".html"
        );

        return  new OutputContentResponse(
                contentStream,
                blogEntity.getContentType(),
                blogEntity.getContentSize(),
                blogEntity.getTitle(),
                blogEntity.getHtmlPath(),
                blogEntity.getId().toString(),
                blogEntity.getAuthor().getUsername()
        );
    }
//
//    @Override
//    public BlogDto getAuthBlog(String username, String blogId) {
//        BlogEntity blogEntity = blogRepository.findById(UUID.fromString(blogId))
//                .orElseThrow(() -> new RuntimeException("Blog not found"));
//
////        if (!blogEntity.getAuthor().equalsIgnoreCase(username)) {
////            throw new RuntimeException("You do not have permission to access this blog");
////        }
////        return createBlogDto(blogEntity);
//        return null;
//    }
//
//
    @Transactional
    @Override
    public void deleteBlog(String username, String blogId) {
        BlogEntity blog = this.blogRepository.findById(UUID.fromString(blogId))
                .orElseThrow(() -> new NoContentException("Blog not found"));

        if (!blog.getAuthor().getUsername().equalsIgnoreCase(username)) {
            throw new RuntimeException("You do not have permission to delete this blog");
        }

        if (fileStorageService.deleteFile(
                "blog." + blog.getAuthor().getUsername().toLowerCase(),
                blog.getId().toString() + ".html"
        )) {
            this.blogRepository.delete(blog);
        } else {
            throw new RuntimeException("Failed to delete blog file from storage");
        }

    }
}

package com.techblog.backend.service.blog;


import com.techblog.backend.dto.blog.BlogDto;
import com.techblog.backend.entity.blog.BlogEntity;
import com.techblog.backend.repository.BlogRepository;
import com.techblog.backend.service.publicInterface.IBlogService;
import org.springframework.stereotype.Service;

@Service
public class BlogService implements IBlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public String postBlog(String username, BlogDto blogDto) {
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setContent(blogDto.getBlogContent());
        blogEntity.setTitle(blogDto.getBlogTitle());
        return blogRepository.save(blogEntity).getId();
    }
}

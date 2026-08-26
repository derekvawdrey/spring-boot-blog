package com.derekvawdrey.blog.post;

import com.derekvawdrey.blog.common.PageResponse;
import com.derekvawdrey.blog.post.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PageResponse<PostDTO> getPosts(int page, int size) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, size));
        return PageResponse.from(posts, PostDTO::from);
    }

    public PostDTO getPostBySlug(String slug) {
        return postRepository.findBySlug(slug)
                .map(PostDTO::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found: " + slug));
    }
}

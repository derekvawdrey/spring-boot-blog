package com.derekvawdrey.blog.post;

import java.util.List;
import java.util.stream.StreamSupport;

import com.derekvawdrey.blog.post.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDTO> getAllPosts() {
        return StreamSupport.stream(postRepository.findAll().spliterator(), false)
                .map(PostDTO::from)
                .toList();
    }
}

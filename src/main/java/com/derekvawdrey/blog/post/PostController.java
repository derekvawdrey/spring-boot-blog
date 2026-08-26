package com.derekvawdrey.blog.post;

import com.derekvawdrey.blog.common.PageResponse;
import com.derekvawdrey.blog.post.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public PageResponse<PostDTO> getPosts(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return postService.getPosts(page, size);
    }

    @GetMapping("/{slug}")
    public PostDTO getPost(@PathVariable String slug) {
        return postService.getPostBySlug(slug);
    }
}

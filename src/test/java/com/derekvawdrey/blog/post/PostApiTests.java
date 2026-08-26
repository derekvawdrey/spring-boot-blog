package com.derekvawdrey.blog.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.derekvawdrey.blog.user.User;
import com.derekvawdrey.blog.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class PostApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getPostsReturnsSavedPostWithAuthor() throws Exception {
        User author = userRepository.save(new User());

        Post post = new Post();
        post.setTitle("Hello");
        post.setBody("World");
        post.setAuthor(author);
        Post savedPost = postRepository.save(post);

        mockMvc.perform(get("/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.id==" + savedPost.getId() + ")].title").value("Hello"))
                .andExpect(jsonPath("$.content[?(@.id==" + savedPost.getId() + ")].body").value("World"))
                .andExpect(jsonPath("$.content[?(@.id==" + savedPost.getId() + ")].slug").value("hello"))
                .andExpect(jsonPath("$.content[?(@.id==" + savedPost.getId() + ")].author.id").value(author.getId().intValue()));
    }

    @Test
    void postsArePaginated() throws Exception {
        long before = postRepository.count();
        for (int i = 0; i < 15; i++) {
            Post post = new Post();
            post.setTitle("Pagination post " + i);
            post.setBody("body");
            postRepository.save(post);
        }
        int total = (int) before + 15;
        int lastPage = (total - 1) / 10;
        int lastPageSize = (int) (total % 10 == 0 ? 10 : total % 10);

        mockMvc.perform(get("/v1/posts").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.content.length()").value(Math.min(10, total)))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.hasNext").value(total > 10));

        mockMvc.perform(get("/v1/posts").param("page", String.valueOf(lastPage)).param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(lastPageSize))
                .andExpect(jsonPath("$.first").value(false))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.hasPrevious").value(lastPage > 0))
                .andExpect(jsonPath("$.hasNext").value(false));
    }

    @Test
    void getPostBySlugReturnsPost() throws Exception {
        Post post = new Post();
        post.setTitle("Hello, World!");
        post.setBody("Body");
        postRepository.save(post);

        mockMvc.perform(get("/v1/posts/hello-world"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("hello-world"))
                .andExpect(jsonPath("$.title").value("Hello, World!"))
                .andExpect(jsonPath("$.body").value("Body"));
    }

    @Test
    void getPostBySlugReturns404WhenMissing() throws Exception {
        mockMvc.perform(get("/v1/posts/does-not-exist"))
                .andExpect(status().isNotFound());
    }
}

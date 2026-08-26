package com.derekvawdrey.blog;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.derekvawdrey.blog.post.Post;
import com.derekvawdrey.blog.post.PostRepository;
import com.derekvawdrey.blog.user.User;
import com.derekvawdrey.blog.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void getAllUsersReturnsSavedUser() throws Exception {
        User saved = userRepository.save(new User());
        String user = "$[?(@.id==" + saved.getId() + ")]";

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(user).exists());
    }

    @Test
    void getAllPostsReturnsSavedPostWithAuthor() throws Exception {
        User author = userRepository.save(new User());

        Post post = new Post();
        post.setTitle("Hello");
        post.setBody("World");
        post.setAuthor(author);
        Post savedPost = postRepository.save(post);
        String saved = "$[?(@.id==" + savedPost.getId() + ")]";

        mockMvc.perform(get("/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(saved + ".title").value("Hello"))
                .andExpect(jsonPath(saved + ".body").value("World"))
                .andExpect(jsonPath(saved + ".author.id").value(author.getId()));
    }
}

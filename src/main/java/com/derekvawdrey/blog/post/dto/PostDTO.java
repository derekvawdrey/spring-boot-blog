package com.derekvawdrey.blog.post.dto;

import com.derekvawdrey.blog.common.dto.BaseDTO;
import com.derekvawdrey.blog.post.Post;
import com.derekvawdrey.blog.user.dto.UserDTO;

public class PostDTO extends BaseDTO {

    private final String slug;
    private final String title;
    private final String body;
    private final UserDTO author;

    public PostDTO(Long id, String slug, String title, String body, UserDTO author) {
        super(id);
        this.slug = slug;
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public static PostDTO from(Post post) {
        UserDTO author = post.getAuthor() == null ? null : UserDTO.from(post.getAuthor());
        return new PostDTO(post.getId(), post.getSlug(), post.getTitle(), post.getBody(), author);
    }

    public String getSlug() {
        return this.slug;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public UserDTO getAuthor() {
        return this.author;
    }
}

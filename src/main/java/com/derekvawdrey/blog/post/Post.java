package com.derekvawdrey.blog.post;

import com.derekvawdrey.blog.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    // todo: Revisit the cascade type, I want soft deletes, not hard deletes
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User author;

    private String title;
    private String body;

    // Getters and setters
    public Long getId() {
        return this.id;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}

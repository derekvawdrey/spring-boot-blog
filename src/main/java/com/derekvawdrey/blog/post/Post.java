package com.derekvawdrey.blog.post;

import com.derekvawdrey.blog.common.Slug;
import com.derekvawdrey.blog.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo: Add soft deletes (e.g. a deleted flag + default filter) instead of hard deletes
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    private String title;
    private String body;

    @Column(nullable = false, unique = true)
    private String slug;

    @PrePersist
    void ensureSlug() {
        if (this.slug == null && this.title != null) {
            String generated = Slug.from(this.title);
            if (!generated.isEmpty()) {
                this.slug = generated;
            }
        }
    }

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

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


}

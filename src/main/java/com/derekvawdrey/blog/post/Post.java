package com.derekvawdrey.blog.post;


public class Post {

    private Long id;
    private Long authorId;
    private String title;
    private String body;

    // Getters and setters
    public Long getId() {
        return this.id;
    }

    public Long getAuthorId() {
        return this.authorId;
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

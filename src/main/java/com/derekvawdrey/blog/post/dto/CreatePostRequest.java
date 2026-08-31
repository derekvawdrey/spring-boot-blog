package com.derekvawdrey.blog.post.dto;

public record CreatePostRequest(String title, String body, boolean published) {
}

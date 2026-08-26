package com.derekvawdrey.blog.common.dto;

public abstract class BaseDTO {

    private final Long id;

    protected BaseDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}

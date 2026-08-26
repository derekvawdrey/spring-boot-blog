package com.derekvawdrey.blog.user.dto;

import com.derekvawdrey.blog.common.dto.BaseDTO;
import com.derekvawdrey.blog.user.User;

public class UserDTO extends BaseDTO {

    public UserDTO(Long id) {
        super(id);
    }

    public static UserDTO from(User user) {
        return new UserDTO(user.getId());
    }
}

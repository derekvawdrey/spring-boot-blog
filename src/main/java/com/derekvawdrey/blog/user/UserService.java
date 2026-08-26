package com.derekvawdrey.blog.user;

import com.derekvawdrey.blog.common.PageResponse;
import com.derekvawdrey.blog.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PageResponse<UserDTO> getUsers(int page, int size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        return PageResponse.from(users, UserDTO::from);
    }
}

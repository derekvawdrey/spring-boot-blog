package com.derekvawdrey.blog.user;

import java.util.List;
import java.util.stream.StreamSupport;

import com.derekvawdrey.blog.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserDTO::from)
                .toList();
    }
}

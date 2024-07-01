package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.models.UserRequest;
import com.example.demo.models.UserResponse;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest userRequest) {
        if (isValidUsername(userRequest.getUserName())) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User();
        user.setUsername(userRequest.getUserName());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setFullName(userRequest.getFullName());

        user = userRepository.save(user);
        return UserResponse.builder()
                .userName(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public boolean isValidUsername(String userName) {
        return userRepository.existsByUsername(userName);
    }
}

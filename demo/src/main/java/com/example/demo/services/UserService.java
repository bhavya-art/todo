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
        if(isValidUsername(userRequest.getUserName()))
        {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User();
        user.setUsername(userRequest.getUserName());
        user.setDatecreated(new Date());
        user.setUserId(user.getUserId());
        user.setPassword(userRequest.getPassword());
        user.setAge(userRequest.getAge());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user=userRepository.save(user);
        return UserResponse.builder().userName(user.getUsername()).dateCreated(user.getDatecreated()).firstName(user.getFirstName()).lastName(user.getLastName()).age(user.getAge()).build();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);

    }

    public boolean isValidUsername(String userName) {
        return userRepository.existsByUsername(userName);
    }
}

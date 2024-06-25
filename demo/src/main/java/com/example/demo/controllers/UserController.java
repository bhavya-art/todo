package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.models.UserRequest;
import com.example.demo.models.UserResponse;
import com.example.demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Controller
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        log.info("Request to create a user: {}", userRequest);
        return ResponseEntity.ok(userService.createUser(userRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> getAllUsers(@PathVariable("id") Long userId) {
        log.info("Request to delete a user");
        userService.deleteUser(userId);
        return ResponseEntity.ok(true);
    }
}


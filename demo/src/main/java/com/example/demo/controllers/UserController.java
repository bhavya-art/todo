package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.models.UserRequest;
import com.example.demo.models.UserResponse;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> getAllUsers(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(true);
    }
}


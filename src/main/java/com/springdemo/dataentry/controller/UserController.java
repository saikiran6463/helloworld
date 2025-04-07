package com.springdemo.dataentry.controller;

import com.springdemo.dataentry.model.User;
import com.springdemo.dataentry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Create User
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // Get all Users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

package com.example.splitwise.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.splitwise.models.User;
import com.example.splitwise.repository.UserRepository;

@RestController
@RequestMapping("user/")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/{id}")
    @ResponseBody User getUser(@PathVariable(name = "id") String id){
        return userRepository.findById(id).orElseThrow();
    }
}

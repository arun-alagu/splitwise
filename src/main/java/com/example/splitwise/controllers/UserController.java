package com.example.splitwise.controllers;

import java.util.UUID;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.splitwise.dtos.CreateUserRequest;
import com.example.splitwise.models.User;
import com.example.splitwise.repository.UserRepository;
import com.example.splitwise.services.UserService;

@RestController
@RequestMapping("user/")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody User getUser(@PathVariable(name = "id") UUID id){
        return userRepository.findById(id).orElseThrow();
    }

    @GetMapping(path = "/all")
    public @ResponseBody List<User> getAllUser(){
        return userRepository.findAll();
    }

    @PostMapping(path = "/create")
    public UUID createUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request).getId();
    }

    // @PutMapping(path = "/{id}")
    // public UUID updateUser(@RequestBody CreateUserRequest request, 
    // @PathVariable(name = "id") UUID id){
    //     return userService.updateUser(request, id).getId();
    // }

    @PatchMapping(path = "/{id}")
    public  UUID updateUser(@RequestBody CreateUserRequest request,
    @PathVariable(name = "id") UUID id){
        return userService.updateUser(request, id).getId();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable(name = "id") UUID id){
        userRepository.deleteById(id);
    }
}

package com.example.splitwise.controllers;

import java.util.UUID;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.splitwise.dtos.UserRequest;
import com.example.splitwise.models.User;
import com.example.splitwise.services.UserService;

@RestController
@RequestMapping("user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody User getUser(@PathVariable(name = "id") UUID userId){
        return userService.getUser(userId);
    }

    @GetMapping(path = "/all")
    public @ResponseBody List<User> getAllUser(){
        return userService.getAllUsers();
    }

    @PostMapping(path = "/create")
    public UUID createUser(@RequestBody UserRequest request){
        return userService.createUser(request).getId();
    }

    // @PutMapping(path = "/{id}")
    // public UUID updateUser(@RequestBody CreateUserRequest request, 
    // @PathVariable(name = "id") UUID id){
    //     return userService.updateUser(request, id).getId();
    // }

    @PatchMapping(path = "/{id}")
    public  UUID updateUser(@RequestBody UserRequest request,
    @PathVariable(name = "id") UUID id){
        return userService.updateUser(request, id).getId();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable(name = "id") UUID userId){
        userService.deleteUser(userId);
    }
}

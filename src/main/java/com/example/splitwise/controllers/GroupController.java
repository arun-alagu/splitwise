package com.example.splitwise.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.splitwise.dtos.CreateGroupRequest;

@RestController
@RequestMapping(path = "group/")
public class GroupController {

    @PostMapping(path = "/create")
    @ResponseBody String createGroup(@RequestBody CreateGroupRequest request){
        return "";
    }
}

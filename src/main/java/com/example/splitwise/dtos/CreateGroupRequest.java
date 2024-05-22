package com.example.splitwise.dtos;

import java.util.List;

import com.example.splitwise.models.User;

import lombok.Data;

@Data
public class CreateGroupRequest {
    private String name;
    private String description;
    private List<User> members;
}

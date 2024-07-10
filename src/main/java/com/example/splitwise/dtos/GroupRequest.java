package com.example.splitwise.dtos;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CreateGroupRequest {
    private String name;
    private String description;
    private List<UUID> members;
    private List<UUID> admins;
}

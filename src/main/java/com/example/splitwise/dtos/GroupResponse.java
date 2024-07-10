package com.example.splitwise.dtos;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetGroupResponse {
    private UUID id;
    private String name;
    private String description;
    private List<UUID> members;
    private List<UUID> admins;
    private UUID createdBy;
}

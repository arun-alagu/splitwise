package com.example.splitwise.dtos;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupResponse {
    private UUID id;
    private String name;
    private String description;
    private Set<UUID> members;
    private Set<UUID> admins;
    private UUID createdBy;
}

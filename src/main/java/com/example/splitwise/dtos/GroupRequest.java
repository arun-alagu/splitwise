package com.example.splitwise.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Builder
public class GroupRequest {
    private String name;
    private String description;
    private Set<UUID> members;
    private Set<UUID> admins;
}

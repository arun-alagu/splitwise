package com.example.splitwise.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class CreateSplitRequest {
    private UUID user;
    private Float amount;
}

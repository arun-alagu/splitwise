package com.example.splitwise.dtos;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String name;
    private Long phoneNumber;



}

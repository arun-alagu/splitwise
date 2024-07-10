package com.example.splitwise.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequest {
    private String email;
    private String name;
    private Long phoneNumber;



}

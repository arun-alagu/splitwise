package com.example.splitwise.dtos;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SplitResponse {
    private UUID id;
    private UUID user;
    private Float amount;
}

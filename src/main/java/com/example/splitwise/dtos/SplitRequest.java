package com.example.splitwise.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Builder
public class SplitRequest {
    private UUID user;
    private Float amount;
}

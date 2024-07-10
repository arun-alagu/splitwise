package com.example.splitwise.dtos;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetSplitResponse {
    private UUID id;
    private UUID user;
    private Float amount;
    private Float percentage;
    private Integer ratio;
}

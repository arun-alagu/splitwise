package com.example.splitwise.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RatioSplitResponse extends SplitResponse{
    private Integer ratio;

    RatioSplitResponse(UUID id, UUID user, Float amount) {
        super(id, user, amount);
    }
}

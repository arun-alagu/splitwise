package com.example.splitwise.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PercentageSplitResponse extends SplitResponse {
    private Float percentage;

    PercentageSplitResponse(UUID id, UUID user, Float amount) {
        super(id, user, amount);
    }
}

package com.example.splitwise.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class PercentageSplitRequest extends SplitRequest {
    private Float percentage;

    PercentageSplitRequest(UUID user, Float amount) {
        super(user, amount);
    }
}

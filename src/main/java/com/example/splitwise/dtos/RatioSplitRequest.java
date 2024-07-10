package com.example.splitwise.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class RatioSplitRequest extends SplitRequest {
    private Integer ratio;

    RatioSplitRequest(UUID user, Float amount) {
        super(user, amount);
    }
}

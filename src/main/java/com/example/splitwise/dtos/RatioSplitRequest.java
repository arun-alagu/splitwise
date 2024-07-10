package com.example.splitwise.dtos;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreateRatioSplitRequest extends CreateSplitRequest {
    private Integer ratio;
}

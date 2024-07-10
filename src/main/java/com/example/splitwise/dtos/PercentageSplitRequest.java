package com.example.splitwise.dtos;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreatePercentageSplitRequest extends CreateSplitRequest {
    private Float percentage;
}

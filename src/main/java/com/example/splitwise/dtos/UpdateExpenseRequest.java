package com.example.splitwise.dtos;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

import com.example.splitwise.models.SplitType;

import lombok.Data;

@Data
public class UpdateExpenseRequest {
    private Float totalAmount;
    private String name;
    private Currency currency;
    private List<GetSplitResponse> paidBy;
    private List<GetSplitResponse> owedBy;
    private SplitType splitType;
    private UUID group;
}

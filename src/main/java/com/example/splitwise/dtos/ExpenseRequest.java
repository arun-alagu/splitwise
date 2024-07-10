package com.example.splitwise.dtos;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

import com.example.splitwise.models.SplitType;

import lombok.Data;

@Data
public class CreateExpenseRequest {
    private Float totalAmount;
    private String name;
    private Currency currency;
    private List<CreateSplitRequest> paidBy;
    private List<CreateSplitRequest> owedBy;
    private SplitType splitType;
    private UUID group;
}

package com.example.splitwise.dtos;

import java.util.Currency;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.splitwise.models.SplitType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExpenseResponse {
    private UUID id;
    private Float totalAmount;
    private String name;
    private Currency currency;
    private Set<SplitResponse> paidBy;
    private Set<SplitResponse> owedBy;
    private SplitType splitType;
    private UUID group;
}

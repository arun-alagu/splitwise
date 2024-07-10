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
public class ExpenseRequest {
    private Float totalAmount;
    private String name;
    private Currency currency;
    private Set<SplitRequest> paidBy;
    private Set<SplitRequest> owedBy;
    private SplitType splitType;
    private UUID group;
}

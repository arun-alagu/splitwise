package com.example.splitwise.models;

import java.util.Currency;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends BaseModel{
    private Float totalAmount;
    private String name;
    private Currency currency;
    @OneToMany
    private List<Split> paidBy;
    @OneToMany
    private List<Split> owedBy;
    @Enumerated(EnumType.ORDINAL)
    private SplitType splitType;
    @ManyToOne
    private Group group;

}

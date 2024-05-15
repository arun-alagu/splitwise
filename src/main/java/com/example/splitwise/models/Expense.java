package com.example.splitwise.models;

import java.util.Currency;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends BaseModel{
    private Double totalAmount;
    private String name;
    private Currency currency;
    @ManyToMany
    private List<User> participants;
    @OneToMany
    private List<ExpenseUser> paidBy;
    @OneToMany
    private List<ExpenseUser> owedBy;
    @Enumerated(EnumType.ORDINAL)
    private SplitType splitType;
    @ManyToOne
    private Group group;

}

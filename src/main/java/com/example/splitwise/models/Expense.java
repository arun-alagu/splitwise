package com.example.splitwise.models;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends BaseModel{
    private Float totalAmount;
    private String name;
    private Currency currency;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Split> paidBy = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Split> owedBy = new HashSet<>();
    @Enumerated(EnumType.ORDINAL)
    private SplitType splitType;
    @ManyToOne
    private Group group;

}

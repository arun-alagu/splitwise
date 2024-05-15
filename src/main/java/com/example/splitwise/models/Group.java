package com.example.splitwise.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "splitwise_group")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Group extends BaseModel{
    private String name;
    private String description;
    @ManyToMany
    private List<User> admins;
    @ManyToMany
    private List<User> members;
    @OneToMany(mappedBy = "group")
    private List<Expense> expenses;
    @ManyToOne
    private User createdBy;
}


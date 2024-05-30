package com.example.splitwise.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.splitwise.models.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense , UUID>{

}

package com.example.splitwise.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.SplitType;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.strategy.SplittingStrategy;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final SplitService splitService;
    private final UserService userService;
    private final GroupService groupService;

    public ExpenseService(ExpenseRepository expenseRepository, SplitService splitService, UserService userService,
            GroupService groupService) {
        this.expenseRepository = expenseRepository;
        this.splitService = splitService;
        this.userService = userService;
        this.groupService = groupService;
    }

    // READ Mapping
    public Expense getExpense(UUID expenseId) {
        return expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense Not Found"));
    }

    public List<Expense> getAllExpense() {
        return expenseRepository.findAll();
    }

    // CREATE Mapping
    public Expense createExpense(Expense expense) {
        expense.setPaidBy(
                expense.getPaidBy().stream().map(
                        splitService::createSplit
                ).collect(Collectors.toSet()));
        expense.setOwedBy(
                expense.getOwedBy().stream().map(
                        splitService::createSplit
                ).collect(Collectors.toSet()));
        return expenseRepository.save(expense);
    }

    // Split Helper
//    private void getSplittingStrategy(SplitType splitType, Expense expense) {
//        switch (splitType) {
//            case PERCENTAGE:
//                SplittingStrategy.getByPercentage(expense);
//                break;
//            case RATIO:
//                SplittingStrategy.getByRatio(expense);
//                break;
//            default:
//                SplittingStrategy.getByEqual(expense);
//                break;
//        }
//    }

    // UPDATE Mapping
    public Expense updateExpense(Expense newExpense, UUID expenseId) throws Exception {
        Optional<Expense> expense = Optional.ofNullable(expenseRepository.findById(expenseId)
                .orElseThrow(() -> new Exception("Expense Not Found!")));

            Optional.ofNullable(newExpense.getName()).ifPresent(expense.get()::setName);
            Optional.ofNullable(newExpense.getTotalAmount()).ifPresent(expense.get()::setTotalAmount);
            Optional.ofNullable(newExpense.getCurrency()).ifPresent(expense.get()::setCurrency);
            Optional.ofNullable(newExpense.getPaidBy()).ifPresent(paidUsers -> {
                splitService.deleteSplits(expense.get().getPaidBy());
                expense.get().getPaidBy().clear();
                expense.get().getPaidBy().addAll(paidUsers.stream().map(splitService::createSplit)
                        .collect(Collectors.toSet()));
            });
            Optional.ofNullable(newExpense.getOwedBy()).ifPresent(owedUsers -> {
                splitService.deleteSplits(expense.get().getOwedBy());
                expense.get().getOwedBy().clear();
                expense.get().getOwedBy().addAll(owedUsers.stream().map(splitService::createSplit)
                        .collect(Collectors.toSet()));
            });
            Optional.ofNullable(newExpense.getSplitType()).ifPresent(expense.get()::setSplitType);
            Optional.ofNullable(newExpense.getGroup()).ifPresent(expense.get()::setGroup);

        return expenseRepository.save(expense.get());
    }

    // DELETE Mapping
    public void deleteExpense(UUID expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}

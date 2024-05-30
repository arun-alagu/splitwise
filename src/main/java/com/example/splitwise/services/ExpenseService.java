package com.example.splitwise.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.splitwise.dtos.CreateExpenseRequest;
import com.example.splitwise.dtos.UpdateExpenseRequest;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Split;
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
    public Expense createExpense(CreateExpenseRequest request) {
        List<Split> paidUsers = request.getPaidBy().stream()
                .map(splitService::createSplit)
                .collect(Collectors.toList());

        List<Split> owedUsers = request.getOwedBy().stream()
                .map(splitService::createSplit)
                .collect(Collectors.toList());

        Expense expense = Expense.builder()
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency())
                .name(request.getName())
                .paidBy(paidUsers)
                .owedBy(owedUsers)
                .splitType(request.getSplitType())
                .group(groupService.getGroup(request.getGroup()))
                .build();

        getSplittingStrategy(request.getSplitType(), expense);

        return expenseRepository.save(expense);
    }

    // Split Helper
    private void getSplittingStrategy(SplitType splitType, Expense expense) {
        switch (splitType) {
            case PERCENTAGE:
                SplittingStrategy.getByPercentage(expense);
                break;
            case RATIO:
                SplittingStrategy.getByRatio(expense);
                break;
            default:
                SplittingStrategy.getByEqual(expense);
                break;
        }
    }

    // UPDATE Mapping
    public Expense updateExpense(UpdateExpenseRequest request, UUID expenseId){
        Expense expense = getExpense(expenseId);

        Optional.ofNullable(request.getName()).ifPresent(expense::setName);
        Optional.ofNullable(request.getTotalAmount()).ifPresent(expense::setTotalAmount);
        Optional.ofNullable(request.getCurrency()).ifPresent(expense::setCurrency);
        Optional.ofNullable(request.getPaidBy()).ifPresent(paidUsers ->{
            expense.setPaidBy(paidUsers.stream().map(paidUser -> splitService.updateSplit(paidUser))
            .collect(Collectors.toList()));
        });
        Optional.ofNullable(request.getOwedBy()).ifPresent(owedUsers ->{
            expense.setOwedBy(owedUsers.stream().map(owedUser -> splitService.updateSplit(owedUser))
            .collect(Collectors.toList()));
        });
        Optional.ofNullable(request.getSplitType()).ifPresent(expense::setSplitType);
        Optional.ofNullable(request.getGroup()).ifPresent(groupId -> {
            expense.setGroup(groupService.getGroup(groupId));
        });
        return expenseRepository.save(expense);
    }

    // DELETE Mapping
    public void deleteExpense(UUID expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}

package com.example.splitwise.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.splitwise.dtos.CreateExpenseRequest;
import com.example.splitwise.dtos.GetExpenseResponse;
import com.example.splitwise.dtos.GetSplitResponse;
import com.example.splitwise.dtos.UpdateExpenseRequest;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Split;
import com.example.splitwise.services.ExpenseService;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public GetExpenseResponse getExpense(@PathVariable(name = "id") UUID expenseId) {
        Expense expense = expenseService.getExpense(expenseId);

        GetExpenseResponse response = GetExpenseResponse.builder()
                .id(expense.getId())
                .totalAmount(expense.getTotalAmount())
                .name(expense.getName())
                .currency(expense.getCurrency())
                .splitType(expense.getSplitType())
                .group(expense.getGroup().getId())
                .build();

        getSplits(expense.getPaidBy(), response::setPaidBy);
        getSplits(expense.getOwedBy(), response::setOwedBy);

        return response;
    }

    // GET Helper
    private void getSplits(List<Split> splits, Consumer<List<GetSplitResponse>> splitResponse) {
        List<GetSplitResponse> response = splits.stream()
                .map(split -> GetSplitResponse.builder()
                        .id(split.getId())
                        .amount(split.getAmount())
                        .user(split.getUser().getId())
                        .percentage(split.getPercentage())
                        .ratio(split.getRatio())
                        .build())
                .collect(Collectors.toList());
        splitResponse.accept(response);
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<GetExpenseResponse> getAllExpenses() {
        List<Expense> allExpense = expenseService.getAllExpense();

        List<GetExpenseResponse> response = new ArrayList<>();

        for (Expense expense : allExpense) {
            response.add(getExpense(expense.getId()));
        }

        return response;
    }

    // POST Mapping
    @PostMapping(path = "/add")
    @ResponseBody
    public UUID createExpense(@RequestBody CreateExpenseRequest request) {
        return expenseService.createExpense(request).getId();
    }

    // PATCH Mapping
    @PatchMapping(path = "/{id}")
    @ResponseBody
    public UUID updateExpense(@RequestBody UpdateExpenseRequest request,
            @PathVariable(name = "id") UUID expenseId) {
        return expenseService.updateExpense(request, expenseId).getId();
    }

    // DELETE Mapping
    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public void deleteExpense(@PathVariable(name = "id") UUID expenseId) {
        expenseService.deleteExpense(expenseId);
    }

}

package com.example.splitwise.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.example.splitwise.services.GroupService;
import com.example.splitwise.services.SplitService;
import com.example.splitwise.services.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.splitwise.dtos.ExpenseRequest;
import com.example.splitwise.dtos.ExpenseResponse;
import com.example.splitwise.dtos.SplitResponse;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Split;
import com.example.splitwise.services.ExpenseService;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;
    private final GroupService groupService;
    private final SplitService splitService;

    public ExpenseController(ExpenseService expenseService, UserService userService, GroupService groupService, SplitService splitService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.groupService = groupService;
        this.splitService = splitService;
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ExpenseResponse getExpense(@PathVariable(name = "id") UUID expenseId) {
        Expense expense = expenseService.getExpense(expenseId);

        ExpenseResponse response = ExpenseResponse.builder()
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
    private void getSplits(Set<Split> splits, Consumer<Set<SplitResponse>> splitResponse) {
        Set<SplitResponse> response = splits.stream()
                .map(split -> SplitResponse.builder()
                        .id(split.getId())
                        .amount(split.getAmount())
                        .user(split.getUser().getId())
                        .build())
                .collect(Collectors.toSet());
        splitResponse.accept(response);
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<ExpenseResponse> getAllExpenses() {
        List<Expense> allExpense = expenseService.getAllExpense();

        List<ExpenseResponse> response = new ArrayList<>();

        for (Expense expense : allExpense) {
            response.add(getExpense(expense.getId()));
        }

        return response;
    }

    // POST Mapping
    @PostMapping(path = "/add")
    @ResponseBody
    public UUID createExpense(@RequestBody ExpenseRequest request) {
        Expense expense = expenseRequestToExpense(request);
        return expenseService.createExpense(expense).getId();
    }

    // PATCH Mapping
    @PatchMapping(path = "/{id}")
    @ResponseBody
    public UUID updateExpense(@RequestBody ExpenseRequest request,
            @PathVariable(name = "id") UUID expenseId) throws Exception {
        Expense expense = expenseRequestToExpense(request);
        return expenseService.updateExpense(expense, expenseId).getId();
    }

    // DELETE Mapping
    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public void deleteExpense(@PathVariable(name = "id") UUID expenseId) {
        expenseService.deleteExpense(expenseId);
    }

    private Expense expenseRequestToExpense(ExpenseRequest request){
        return Expense.builder()
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency())
                .name(request.getName())
                .splitType(request.getSplitType())
                .paidBy(request.getPaidBy().stream().map(
                        splitRequest -> Split.builder()
                                .amount(splitRequest.getAmount())
                                .user(userService.getUser(splitRequest.getUser()))
                                .build()
                ).collect(Collectors.toSet()))
                .owedBy(request.getOwedBy().stream().map(
                        splitRequest -> Split.builder()
                                .amount(splitRequest.getAmount())
                                .user(userService.getUser(splitRequest.getUser()))
                                .build()
                ).collect(Collectors.toSet()))
                .group(groupService.getGroup(request.getGroup()))
                .build();
    }

}

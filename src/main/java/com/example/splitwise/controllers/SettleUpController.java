package com.example.splitwise.controllers;

import com.example.splitwise.models.Expense;
import com.example.splitwise.services.SettleUpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/settle-up")
public class SettleUpController {

    private final SettleUpService settleUpService;

    public SettleUpController(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }

    @GetMapping("/group/{id}")
    public Set<Expense> settleUp(
            @PathVariable(value = "id")UUID groupId
            ){
        return settleUpService.settleUpGroup(groupId);
    }
}

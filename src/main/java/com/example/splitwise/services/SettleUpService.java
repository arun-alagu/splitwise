package com.example.splitwise.services;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Group;
import com.example.splitwise.strategy.SimplifiedSettleUpStrategy;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class SettleUpService {
    private final GroupService groupService;

    public SettleUpService(GroupService groupService) {
        this.groupService = groupService;
    }

    public Set<Expense> settleUpGroup(UUID groupId){
        Group settleUpGroup = groupService.getGroup(groupId);
        return SimplifiedSettleUpStrategy.settleUp(settleUpGroup.getExpenses());
    }
}

package com.example.splitwise.strategy;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Split;


public class SplittingStrategy {

    public static void getByEqual(Expense expense) {
        Integer totalParticipants = expense.getOwedBy().size();
        for (Split split : expense.getOwedBy()) {
            split.setAmount(expense.getTotalAmount() / totalParticipants);
        }
    }

    public static void getByPercentage(Expense expense) {
        for (Split split : expense.getOwedBy()) {
            split.setAmount(expense.getTotalAmount()*(split.getPercentage()/100));
        }
    }
    
    public static void getByRatio(Expense expense){
        Integer totalParts = 0;
        Float partAmount = 0.0f;
        for (Split split : expense.getOwedBy()) {
            totalParts += split.getRatio();
        }
        partAmount = expense.getTotalAmount() / totalParts;
        for (Split split : expense.getOwedBy()) {
            split.setAmount(split.getRatio()*partAmount);
        }
    }
}

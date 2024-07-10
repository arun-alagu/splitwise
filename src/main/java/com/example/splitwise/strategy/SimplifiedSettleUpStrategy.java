package com.example.splitwise.strategy;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Split;
import com.example.splitwise.models.User;

import java.util.*;

public class SimplifiedSettleUpStrategy {

    public static Set<Expense> settleUp(Set<Expense> expenses){
        Map<User, Float> paidAndOwedBy = new HashMap<>();

        for(Expense expense : expenses){
            for(Split split : expense.getPaidBy()){
                paidAndOwedBy.put(split.getUser(),
                        paidAndOwedBy.getOrDefault(split.getUser(), 0f)
                                + split.getAmount());
            }

            for(Split split : expense.getOwedBy()){
                paidAndOwedBy.put(split.getUser(),
                        paidAndOwedBy.getOrDefault(split.getUser(), 0f)
                                - split.getAmount());
            }
        }

        PriorityQueue<Split> paidBy = new PriorityQueue<>(new PqComparator());
        PriorityQueue<Split> owedBy = new PriorityQueue<>(new PqComparator());

        for(Map.Entry<User, Float> paidAndOwedMap : paidAndOwedBy.entrySet()){
            if(paidAndOwedMap.getValue() > 0){
                paidBy.add(
                        Split.builder()
                                .user(paidAndOwedMap.getKey())
                                .amount(paidAndOwedMap.getValue())
                                .build()
                );
            }
            else{
                owedBy.add(
                        Split.builder()
                                .user(paidAndOwedMap.getKey())
                                .amount(-1 * paidAndOwedMap.getValue())
                                .build()
                );
            }
        }

        Set<Expense> transactions = new HashSet<>();

        while(!paidBy.isEmpty()){
            Split paidUser = paidBy.poll();
            Split owedUser = owedBy.poll();

            if(paidUser.getAmount() > owedUser.getAmount()){
                transactions.add(Expense.builder()
                        .owedBy(Set.of(new Split(paidUser.getUser(), owedUser.getAmount())))
                        .paidBy(Set.of(owedUser))
                        .build());
                paidBy.add(new Split(paidUser.getUser(), paidUser.getAmount() - owedUser.getAmount()));
            }
            else{
                transactions.add(Expense.builder()
                        .owedBy(Set.of(paidUser))
                        .paidBy(Set.of(new Split(owedUser.getUser(), paidUser.getAmount())))
                        .build());
                owedBy.add(new Split(owedUser.getUser(), owedUser.getAmount() - paidUser.getAmount()));
            }
        }

        return transactions;
    }

    static class PqComparator implements Comparator<Split> {

        @Override
        public int compare(Split u1, Split u2) {
            if (!Objects.equals(u1.getAmount(), u2.getAmount())) {
                if (u1.getAmount() < u2.getAmount()) {
                    return 1;
                } else {
                    return -1;
                }
            }

            return 1;
        }
    }
}

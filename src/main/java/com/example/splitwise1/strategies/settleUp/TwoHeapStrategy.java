package com.example.splitwise1.strategies.settleUp;

import com.example.splitwise1.models.Expense;
import com.example.splitwise1.models.ExpenseUser;
import com.example.splitwise1.models.UserExpenseType;
import com.example.splitwise1.repositories.ExpenseUserRepository;
import com.example.splitwise1.services.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TwoHeapStrategy implements SettleUpStrategy {
    ExpenseUserRepository expenseUserRepository;

    @Autowired
    public TwoHeapStrategy(ExpenseUserRepository expenseUserRepository) {
        this.expenseUserRepository = expenseUserRepository;
    }

    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {

        Map<Long, Long> userAmountMap = new HashMap<>();
        for(Expense expense : expenses){
            List<ExpenseUser> expenseUserList = expenseUserRepository.findAllByExpense(expense);
            for(ExpenseUser expenseUser : expenseUserList){

                if(expenseUser.getUserExpenseType().equals(UserExpenseType.PAID)){
                    userAmountMap.put(expenseUser.getUser().getId(), userAmountMap.getOrDefault(expenseUser.getUser().getId(),0L)+expenseUser.getAmount());
                }
                if(expenseUser.getUserExpenseType().equals(UserExpenseType.HAS_TO_PAY)){
                    userAmountMap.put(expenseUser.getUser().getId(), userAmountMap.getOrDefault(expenseUser.getUser().getId(),0L)-expenseUser.getAmount());
                }
            }
        }
        PriorityQueue<Pair> maxHeap  = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Pair> minHeap  = new PriorityQueue<>();

        for(Map.Entry<Long, Long> uAm : userAmountMap.entrySet()){
                if(uAm.getValue()>0){
                    maxHeap.add(new Pair(uAm.getKey(), uAm.getValue()));
                }else if(uAm.getValue()<0){
                    minHeap.add(new Pair(uAm.getKey(), uAm.getValue()));
                }
        }
        List<Transaction> transactions = new ArrayList<>();
        while(maxHeap.size()!=0 && minHeap.size()!=0){
            Pair maxPair = maxHeap.poll();
            Pair minPair = minHeap.poll();
            Long maxAmount = maxPair.getAmount();
            Long minAmount = minPair.getAmount()*-1;
            if(maxAmount > minAmount){
                Long amt = maxAmount - minAmount;
                transactions.add(new Transaction(minAmount, minPair.getUserId(), maxPair.getUserId()));
                maxHeap.add(new Pair(maxPair.getUserId(), amt));
            }else if(maxAmount < minAmount){
                Long amt = minAmount - maxAmount;
                transactions.add(new Transaction(minAmount, minPair.getUserId(), maxPair.getUserId()));
                minHeap.add(new Pair(minPair.getUserId(), (amt*-1)));
            }else{
                transactions.add(new Transaction(minAmount, minPair.getUserId(), maxPair.getUserId()));
            }
        }

return transactions;
    }
}
//        PriorityQueue<ExpenseUser> maxHeapForPaid = new PriorityQueue<>(Collections.reverseOrder());
//        PriorityQueue<ExpenseUser> maxHeapForHasToPay = new PriorityQueue<>(Collections.reverseOrder());
//
//        for(Expense expense : expenses){
//            List<ExpenseUser> expenseUserList = expenseUserRepository.findAllByExpense(expense);
//            for(ExpenseUser expenseUser : expenseUserList){
//                if(expenseUser.getUserExpenseType().equals(UserExpenseType.PAID)){
//                    maxHeapForPaid.add(expenseUser);
//                }
//                if(expenseUser.getUserExpenseType().equals(UserExpenseType.HAS_TO_PAY)){
//                    maxHeapForHasToPay.add(expenseUser);
//                }
//            }
//        }
//        List<Transaction> transactions =new ArrayList<>();
//
//        Transaction transaction;
//
//        while(maxHeapForPaid.size()!=0 && maxHeapForHasToPay.size()!=0){
//
//            ExpenseUser expenseUser1 = maxHeapForPaid.poll();
//            ExpenseUser expenseUser2 = maxHeapForHasToPay.poll();
//            Long maxPaid= expenseUser1.getAmount();
//            Long maxToPay= expenseUser2.getAmount();
//            if(maxPaid > maxToPay){
//                ExpenseUser expenseUser = new ExpenseUser(expenseUser1);
//                expenseUser.setAmount(maxPaid-maxToPay);
//                maxHeapForPaid.add(expenseUser);
//                transactions.add(new Transaction(maxToPay, expenseUser2.getUser(), expenseUser1.getUser()));
//            }else if(maxPaid < maxToPay){
//                ExpenseUser expenseUser = new ExpenseUser(expenseUser2);
//                expenseUser.setAmount(maxToPay-maxPaid);
//                maxHeapForPaid.add(expenseUser);
//                transactions.add(new Transaction(maxPaid, expenseUser2.getUser(), expenseUser1.getUser()));
//            }else {
//                transactions.add(new Transaction(maxPaid, expenseUser2.getUser(), expenseUser1.getUser()));
//            }
//        }
//        return transactions;

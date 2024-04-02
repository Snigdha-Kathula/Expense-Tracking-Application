package com.example.splitwise1.services;

import com.example.splitwise1.exceptions.GroupNotFoundException;
import com.example.splitwise1.exceptions.UserNotFoundException;
import com.example.splitwise1.models.*;
import com.example.splitwise1.repositories.ExpenseRepository;
import com.example.splitwise1.repositories.ExpenseUserRepository;
import com.example.splitwise1.repositories.GroupRepository;
import com.example.splitwise1.repositories.UserRepository;
import com.example.splitwise1.strategies.settleUp.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class SettleService {
    UserRepository userRepository;
    ExpenseUserRepository expenseUserRepository;
    GroupRepository groupRepository;
    ExpenseRepository expenseRepository;
    SettleUpStrategy settleUpStrategy;
    @Autowired
    public SettleService(UserRepository userRepository, ExpenseUserRepository expenseUserRepository, GroupRepository groupRepository, ExpenseRepository expenseRepository, SettleUpStrategy settleUpStrategy) {
        this.userRepository = userRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
        this.settleUpStrategy = settleUpStrategy;
    }


    public Long myTotal(Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        List<ExpenseUser> expenseUserList = expenseUserRepository.findAllByUser(user);
        Long sumPaid=0L;
        Long sumShouldPay=0L;
        for(ExpenseUser expenseUser: expenseUserList){
            if(expenseUser.getUserExpenseType().equals(UserExpenseType.PAID)){
                sumPaid += expenseUser.getAmount();
            }else{
                sumShouldPay += expenseUser.getAmount();
            }
        }
        Long total = sumPaid - sumShouldPay;
        return total;
    }

    public void settleUpUser(Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        List<ExpenseUser> expenseUserList =expenseUserRepository.findAllByUser(userOptional.get());
        HashSet<Long> setExpenseIds = new HashSet<>();
        for(ExpenseUser expenseUser : expenseUserList){
             setExpenseIds.add(expenseUser.getExpense().getId());
        }
        List<Long> expenseIds = new ArrayList<>();
        for(Long st : setExpenseIds){
            expenseIds.add(st);
        }

        List<Expense> expenses = new ArrayList<>();
        for(Long l : expenseIds){
            Optional<Expense> expenseOptional =  expenseRepository.findById(l);
            if(expenseOptional.isEmpty()){
                throw new RuntimeException();
            }
            expenses.add(expenseOptional.get());
        }
        List<Transaction> transactions =settleUpStrategy.settleUp(expenses);
//        List<Transaction> filteredTransactions = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(transaction.getFrom().equals(user.getId()) || transaction.getTo().equals(user.getId())){

                System.out.println("From "+transaction.getFrom()+" to "+transaction.getTo()+" Amount :"+transaction.getAmount());
            }
        }
    }

    public void settleUpGroup(Long userId, Long grpId) throws UserNotFoundException, GroupNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        Optional<Group> groupOptional = groupRepository.findById(grpId);
        if (groupOptional.isEmpty()){
            throw new GroupNotFoundException();
        }
        List<Expense> expenses = groupOptional.get().getExpenses();
        List<Transaction> transactions =settleUpStrategy.settleUp(expenses);
//        List<Transaction> filteredTransactions = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(transaction.getFrom().equals(user.getId()) || transaction.getTo().equals(user.getId())){

                System.out.println("From "+transaction.getFrom()+" to "+transaction.getTo()+" Amount :"+transaction.getAmount());
            }
        }


    }
}

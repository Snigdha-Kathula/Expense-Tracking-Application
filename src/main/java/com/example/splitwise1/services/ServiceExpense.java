package com.example.splitwise1.services;
import com.example.splitwise1.exceptions.GroupNotFoundException;
import com.example.splitwise1.exceptions.InputWrongException;
import com.example.splitwise1.exceptions.UserNotFoundException;
import com.example.splitwise1.exceptions.UserNotPartOfGroupException;
import com.example.splitwise1.models.*;
import com.example.splitwise1.repositories.ExpenseRepository;
import com.example.splitwise1.repositories.ExpenseUserRepository;
import com.example.splitwise1.repositories.GroupRepository;
import com.example.splitwise1.repositories.UserRepository;
import com.example.splitwise1.strategies.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.splitwise1.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceExpense {
    UserRepository userRepository;
    GroupRepository groupRepository;
    ExpenseRepository expenseRepository;
    ExpenseUserRepository expenseUserRepository;
    @Autowired
    public ServiceExpense(UserRepository userRepository, GroupRepository groupRepository, ExpenseRepository expenseRepository, ExpenseUserRepository expenseUserRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
        this.expenseUserRepository = expenseUserRepository;
    }

    public void createExpense(List<Long> userIds, Long grpId, List<Long> paid, String splitWord, List<Long> hadToPay, String desc) throws UserNotFoundException, GroupNotFoundException, UserNotPartOfGroupException,  InputWrongException {

        SplitWordStrategy splitWordStrategy;
        Expense expense = new Expense();
        List<User> userList =new ArrayList<>();
        Long creatorUserId =userIds.get(0);

        Optional<User> userOptional = userRepository.findById(creatorUserId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User creator = userOptional.get();
        Group group = new Group();
        if(grpId == null){
            for(int i=0;i<userIds.size();i++){
                Optional<User> userOpt = userRepository.findById(userIds.get(i));
                if(userOpt.isEmpty()){
                    throw new UserNotFoundException();
                }
                userList.add(userOpt.get());
            }
        }else {
            Optional<Group> groupOptional = groupRepository.findById(grpId);
            if (groupOptional.isEmpty()) {
                throw new GroupNotFoundException();
            }
            group = groupOptional.get();

            userList = group.getUsers();
            boolean flag=false;
            for(int i=0;i<userList.size();i++){
                if(userList.get(i).getId().equals(creator.getId())){
                    flag =true;
                }
            }
            if(!flag) throw new UserNotPartOfGroupException();
        }
        if(paid.size() > userList.size()) throw new InputWrongException();
        if(hadToPay.size() > userList.size()) throw new InputWrongException();
        if(splitWord.equals("Equal")){
            splitWordStrategy = new Equal();
        } else if (splitWord.equals("Exact")) {
            splitWordStrategy =new Exact();
        } else if (splitWord.equals("Ratio")) {
            splitWordStrategy = new Ratio();
        } else {
            splitWordStrategy = new Percent();
        }
        Long sumOfPaidList=0L;
        for(int i =0; i< paid.size();i++){
            sumOfPaidList += paid.get(i);
        }
        List<Long> hasToPay = splitWordStrategy.convertToAmount(userList, hadToPay, sumOfPaidList);

        Long sumOfHadToPayList=0L;
        for(int i=0;i<hasToPay.size();i++){
            sumOfHadToPayList += hasToPay.get(i);
        }
        if(!sumOfPaidList.equals(sumOfHadToPayList)) throw new InputWrongException();

        expense.setDescription(desc);
        expense.setCreatedBy(creator);
        expense.setExpenseType(ExpenseType.REAL);

        expense.setAmount(sumOfPaidList);
        expenseRepository.save(expense);
        if(grpId!=null){
            List<Expense> expenseList = group.getExpenses();
            expenseList.add(expense);
            groupRepository.save(group);
        }

        for(int i =0; i< paid.size();i++){
            ExpenseUser expenseUser =new ExpenseUser();
            expenseUser.setExpense(expense);
            expenseUser.setUserExpenseType(UserExpenseType.PAID);
            expenseUser.setAmount(paid.get(i));
            expenseUser.setUser(userList.get(i));
            expenseUserRepository.save(expenseUser);
        }
        for(int i =0; i< hasToPay.size();i++){
            ExpenseUser expenseUser =new ExpenseUser();
            expenseUser.setExpense(expense);
            expenseUser.setUserExpenseType(UserExpenseType.HAS_TO_PAY);
            expenseUser.setAmount(hasToPay.get(i));
            expenseUser.setUser(userList.get(i));
            expenseUserRepository.save(expenseUser);
        }

    }



}

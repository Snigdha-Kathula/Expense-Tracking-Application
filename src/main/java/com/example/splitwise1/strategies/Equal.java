package com.example.splitwise1.strategies;

import com.example.splitwise1.models.User;

import java.util.List;

public class Equal implements SplitWordStrategy{
    @Override
    public List<Long> convertToAmount(List<User> userList, List<Long> splits, Long totalAmount) {
        Long single =totalAmount/userList.size();
        for(int i=0;i<userList.size();i++){
            splits.add(single);
        }
        return splits;
    }
}

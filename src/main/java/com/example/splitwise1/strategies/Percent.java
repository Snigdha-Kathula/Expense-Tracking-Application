package com.example.splitwise1.strategies;

import com.example.splitwise1.models.User;

import java.util.List;

public class Percent implements SplitWordStrategy{
    @Override
    public List<Long> convertToAmount(List<User> userList, List<Long> splits, Long totalAmount) {
        for(int i=0;i<userList.size();i++){
            if(i<splits.size()){
                Long amount = (splits.get(i)*totalAmount)/100;
                splits.set(i, amount);
            }else{
                splits.add(0L);
            }

        }
        return splits;
    }
}

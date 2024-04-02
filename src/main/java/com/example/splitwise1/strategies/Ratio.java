package com.example.splitwise1.strategies;

import com.example.splitwise1.models.User;

import java.util.List;

public class Ratio implements SplitWordStrategy{
    @Override
    public List<Long> convertToAmount(List<User> userList, List<Long> splits, Long totalAmount) {
        Long sum=0l;
        for(Long l:splits){
            sum+=l;
        }
        for(int i=0;i<userList.size();i++){
            if(i<splits.size()){
                Long amount = (totalAmount*splits.get(i))/sum;
                splits.set(i, amount);
            }else{
                splits.add(0L);
            }

        }
        return splits;
    }
}

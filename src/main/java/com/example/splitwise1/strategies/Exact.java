package com.example.splitwise1.strategies;

import com.example.splitwise1.models.User;

import java.util.List;
 public class Exact implements SplitWordStrategy{
    @Override
    public List<Long> convertToAmount(List<User> userList, List<Long> splits, Long totalAmount) {

        return splits;
    }
}

package com.example.splitwise1.strategies.settleUp;


import com.example.splitwise1.models.Expense;
import com.example.splitwise1.services.Transaction;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settleUp(List<Expense> expenses);
}

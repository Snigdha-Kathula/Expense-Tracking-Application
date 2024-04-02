package com.example.splitwise1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExpenseUser extends BaseModel implements Comparable<ExpenseUser>{
    @ManyToOne
    private User user;
    private Long amount;
    @ManyToOne
    private Expense expense;
    @Enumerated(EnumType.ORDINAL)
    private UserExpenseType userExpenseType;

    public ExpenseUser() {
    }
    public ExpenseUser(ExpenseUser expenseUser){
        this.user = expenseUser.getUser();
        this.expense = expenseUser.getExpense();
        this.amount = expenseUser.getAmount();
        this.userExpenseType = expenseUser.getUserExpenseType();
    }

    @Override
    public int compareTo(ExpenseUser expenseUser) {
         return Long.compare(this.amount, expenseUser.getAmount());
    }
}

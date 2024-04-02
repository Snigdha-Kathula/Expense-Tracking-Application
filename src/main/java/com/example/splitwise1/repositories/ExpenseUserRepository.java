package com.example.splitwise1.repositories;

import com.example.splitwise1.models.Expense;
import com.example.splitwise1.models.ExpenseUser;
import com.example.splitwise1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, Long> {
    List<ExpenseUser> findAllByUser(User user);
    List<ExpenseUser> findAllByExpense(Expense expense);
}

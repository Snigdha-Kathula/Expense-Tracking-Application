package com.example.splitwise1.services;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private Long amount;
    private Long from;
    private Long to;

    public Transaction() {
    }

    public Transaction(Long amount, Long from, Long to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
    }
}

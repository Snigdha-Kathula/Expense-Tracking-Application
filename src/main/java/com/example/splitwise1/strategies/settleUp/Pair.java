package com.example.splitwise1.strategies.settleUp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair implements Comparable<Pair>{
    private Long userId;
    private Long amount;

    public Pair(Long key, Long value) {
        this.userId = key;
        this.amount = value;
    }

    @Override
    public int compareTo(Pair pair) {

        return Long.compare(this.amount, pair.getAmount());
    }
}

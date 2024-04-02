package com.example.splitwise1.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity(name = "swgroup")
public class Group extends BaseModel{
    private String name;
    @ManyToOne
    private User admin;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> users;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Expense> expenses;
}

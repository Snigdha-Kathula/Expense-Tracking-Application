package com.example.splitwise1.commands;

public interface Command {
    boolean matches(String input);
    void execute(String input);

}

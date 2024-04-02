package com.example.splitwise1.commands;

import com.example.splitwise1.controllers.SettleController;
import com.example.splitwise1.dtos.TotalRequestDto;
import com.example.splitwise1.dtos.TotalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyTotalCommand implements Command{
    SettleController settleController;
    @Autowired
    public MyTotalCommand(SettleController settleController) {
        this.settleController = settleController;
    }

    @Override
    public boolean matches(String input) {
        String[] split = input.split(" ");
        return (split.length == 2 && split[1].equals(CommandKeywords.myTotal));
    }

    @Override
    public void execute(String input) {
        String[] split = input.split(" ");
        Long userId = Long.parseLong(split[0]);
        TotalRequestDto request =new TotalRequestDto();
        request.setUserId(userId);
        TotalResponseDto response = settleController.myTotal(request);
        System.out.println(response.getMessage());

    }
}
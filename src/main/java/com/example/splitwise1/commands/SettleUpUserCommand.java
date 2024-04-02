package com.example.splitwise1.commands;

import com.example.splitwise1.controllers.SettleController;
import com.example.splitwise1.dtos.SettleUpRequestDto;
import com.example.splitwise1.dtos.SettleUpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUpUserCommand implements Command{
    SettleController settleController;
    @Autowired
    public SettleUpUserCommand(SettleController settleController) {
        this.settleController = settleController;
    }

    @Override
    public boolean matches(String input) {
        String[] split = input.split(" ");
        return (split.length == 2 && split[1].equals(CommandKeywords.settleUp));
    }

    @Override
    public void execute(String input) {
        String[] split = input.split(" ");
        Long userId = Long.parseLong(split[0]);
        SettleUpRequestDto request =new SettleUpRequestDto();
        request.setId(userId);
        SettleUpResponseDto response =settleController.settleUpUser(request);
        System.out.println(response.getMessage());

    }
}

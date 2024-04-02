package com.example.splitwise1.commands;

import com.example.splitwise1.controllers.SettleController;
import com.example.splitwise1.dtos.SettleUpGroupRequestDto;
import com.example.splitwise1.dtos.SettleUpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUpGroupCommand implements Command{
    SettleController settleController;
    @Autowired
    public SettleUpGroupCommand(SettleController settleController) {
        this.settleController = settleController;
    }

    @Override
    public boolean matches(String input) {
        String[] split = input.split(" ");
        return (split.length == 3 && split[1].equals(CommandKeywords.settleUp));
    }

    @Override
    public void execute(String input) {
        String[] split = input.split(" ");
        Long userId = Long.parseLong(split[0]);
        Long grpId = Long.parseLong(split[2]);
        SettleUpGroupRequestDto request =new SettleUpGroupRequestDto();
        request.setUserId(userId);
        request.setGrpId(grpId);
        SettleUpResponseDto response =settleController.settleUpGroup(request);
        System.out.println(response.getMessage());

    }
}

package com.example.splitwise1.commands;

import com.example.splitwise1.controllers.GroupController;
import com.example.splitwise1.dtos.AddMemberRequestDto;
import com.example.splitwise1.dtos.AddMemeberResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddMemberCommand implements Command{
    GroupController addGroupController ;
    @Autowired
    public AddMemberCommand(GroupController addGroupController) {
        this.addGroupController = addGroupController;
    }

    @Override
    public boolean matches(String input) {
         String[] split = input.split(" ");
         return (split.length == 4 && split[1].equals(CommandKeywords.addMember));
    }

    @Override
    public void execute(String input) {
        String[] split = input.split(" ");
        Long user1Id = Long.parseLong(split[0]);
        Long grpId = Long.parseLong(split[2]);
        Long user2Id = Long.parseLong(split[3]);
        AddMemberRequestDto request = new AddMemberRequestDto();
        request.setU1Id(user1Id);
        request.setGId(grpId);
        request.setU2Id(user2Id);
        AddMemeberResponseDto response = addGroupController.addMember(request);
        System.out.println(response.getResponseState());
        System.out.println(response.getMessage());


    }
}

package com.example.splitwise1.commands;

import com.example.splitwise1.controllers.GroupController;
import com.example.splitwise1.dtos.CommonResponseDto;
import com.example.splitwise1.dtos.GroupsRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupsCommand implements Command{
    GroupController addGroupController ;
    @Autowired
    public GroupsCommand(GroupController addGroupController) {
        this.addGroupController = addGroupController;
    }

    @Override
    public boolean matches(String input) {
        String[] split = input.split(" ");
        return (split.length == 2 && split[1].equals(CommandKeywords.groups));
    }

    @Override
    public void execute(String input) {
        String[] split = input.split(" ");
        Long userID = Long.parseLong(split[0]);
        GroupsRequestDto request = new GroupsRequestDto();
        request.setUserID(userID);
        CommonResponseDto response = addGroupController.findGroupsInvolved(request);
        System.out.println(response.getMessage());
    }
}

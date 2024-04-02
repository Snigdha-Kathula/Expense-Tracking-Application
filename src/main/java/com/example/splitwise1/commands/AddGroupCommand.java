package com.example.splitwise1.commands;
import com.example.splitwise1.controllers.GroupController;
import com.example.splitwise1.dtos.AddGroupRequestDto;
import com.example.splitwise1.dtos.AddGroupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddGroupCommand implements Command{
    GroupController addGroupController;
    @Autowired
    public AddGroupCommand(GroupController addGroupController) {
        this.addGroupController = addGroupController;
    }

    @Override
    public boolean matches(String input) {
        String[] split = input.split(" ");
        return (split.length == 3 && split[1].equals(CommandKeywords.addGroup));
    }

    @Override
    public void execute(String input) {
        String[] split = input.split(" ");
        String groupName = split[2];
        String adminEmail = split[0];
        AddGroupRequestDto request = new AddGroupRequestDto();
        request.setAdminEmail(adminEmail);
        request.setGroupName(groupName);
        AddGroupResponseDto response = addGroupController.addGroup(request);
        System.out.println(response.getResponseState());
        System.out.println(response.getMessage());
    }
}

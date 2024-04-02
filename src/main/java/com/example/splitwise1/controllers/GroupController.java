package com.example.splitwise1.controllers;

import com.example.splitwise1.dtos.*;
import com.example.splitwise1.exceptions.*;
import com.example.splitwise1.models.Group;
import com.example.splitwise1.models.User;
import com.example.splitwise1.services.AddGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController {
    AddGroupService addGroupService;
    @Autowired
    public GroupController(AddGroupService addGroupService) {
        this.addGroupService = addGroupService;
    }

    public AddGroupResponseDto addGroup(AddGroupRequestDto request) {
        String adminEmail =request.getAdminEmail();
        String groupName =request.getGroupName();
        AddGroupResponseDto addGroupResponseDto = new AddGroupResponseDto();
        User user;
        try{
            user = addGroupService.addGroup(
                    request.getGroupName(), request.getAdminEmail()
            );
        }catch (UserNotFoundException u){
            addGroupResponseDto.setResponseState(ResponseState.FAILURE);
            addGroupResponseDto.setMessage("User : does not exist. "+adminEmail+" is not registered.");
            return addGroupResponseDto;
        }catch (GroupAlreadyExits e) {
            addGroupResponseDto.setResponseState(ResponseState.FAILURE);
            addGroupResponseDto.setMessage("This group name Already Exist in "+groupName);
            return addGroupResponseDto;
        }
        addGroupResponseDto.setMessage("The Group "+groupName+"has been created by "+user.getName()+". who has email as"+adminEmail+".");
        addGroupResponseDto.setResponseState(ResponseState.SUCCESS);
        return  addGroupResponseDto;
    }

    public AddMemeberResponseDto addMember(AddMemberRequestDto request) {
        AddMemeberResponseDto addMemeberResponseDto = new AddMemeberResponseDto();
        Group group;
        try{
            group = addGroupService.addMember(
                    request.getU1Id(), request.getGId(), request.getU2Id()
            );
        }catch (GroupNotFoundException e){
            addMemeberResponseDto.setMessage("Exception : Group Does Not Exist ");
            addMemeberResponseDto.setResponseState(ResponseState.FAILURE);
            return addMemeberResponseDto;
        }catch (UserNotFoundException e){
            addMemeberResponseDto.setMessage("Exception : User not found (User not Registered ) ");
            addMemeberResponseDto.setResponseState(ResponseState.FAILURE);
            return addMemeberResponseDto;
        }catch (User1NotAdminOfGroupException e){
            addMemeberResponseDto.setMessage("Exception : User1 is not Admin of the Group (Only Admins can Add the Members into the Group)");
            addMemeberResponseDto.setResponseState(ResponseState.FAILURE);
            return addMemeberResponseDto;
        }catch (UserAlreadyExitsException e){
            addMemeberResponseDto.setMessage("Exception : User2 Already a Member of Group");
            addMemeberResponseDto.setResponseState(ResponseState.FAILURE);
            return addMemeberResponseDto;

        }
        addMemeberResponseDto.setMessage("user1  Added the user2  in the Group" );
        addMemeberResponseDto.setResponseState(ResponseState.SUCCESS);
        return addMemeberResponseDto;
    }

    public CommonResponseDto findGroupsInvolved(GroupsRequestDto request) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        String groupsnames ;
        try{
            groupsnames =addGroupService.findGroupsInvolved(
                    request.getUserID()
            );
        }catch (UserNotFoundException e){
            commonResponseDto.setMessage("User Not Found : Enter valid UserId");
            commonResponseDto.setResponseState(ResponseState.FAILURE);
            return commonResponseDto;
        }
        commonResponseDto.setResponseState(ResponseState.SUCCESS);
        if(groupsnames.isEmpty()){
            commonResponseDto.setMessage("User Not Involved in Any Group");
             return commonResponseDto;
        }
        commonResponseDto.setMessage("The User Involved Groups are : "+groupsnames);
        return commonResponseDto;
    }
}

package com.example.splitwise1.services;

import com.example.splitwise1.exceptions.*;
import com.example.splitwise1.models.Group;
import com.example.splitwise1.models.User;
import com.example.splitwise1.repositories.GroupRepository;
import com.example.splitwise1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddGroupService {
    UserRepository userRepository;
    GroupRepository groupRepository;
    @Autowired
    public AddGroupService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }
    public User addGroup(String groupName, String adminEmail) throws UserNotFoundException, GroupAlreadyExits {
        Optional<User> userOptional = userRepository.findByEmail(adminEmail);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        List<Group> usergroup = groupRepository.findAllByAdmin(user);
        for(Group group : usergroup){
            if(group.getName().equals(groupName)) throw new GroupAlreadyExits();
        }
        Group group = new Group();
        group.setName(groupName);
        group.setAdmin(user);
        List<User> noOfUsersIngroup = new ArrayList<>();
        noOfUsersIngroup.add(user);
        group.setUsers(noOfUsersIngroup);
        groupRepository.save(group);
        return user;
    }
    public Group addMember(Long u1Id, Long gId, Long u2Id) throws GroupNotFoundException, UserNotFoundException, User1NotAdminOfGroupException, UserAlreadyExitsException {
        Optional<Group> groupOptional = groupRepository.findById(gId);
        Optional<User> user1Optional = userRepository.findById(u1Id);
        Optional<User> user2Optional =userRepository.findById(u2Id);
        if(groupOptional.isEmpty()){
            throw new GroupNotFoundException();
        }
        if(user1Optional.isEmpty() || user2Optional.isEmpty()){
            throw new UserNotFoundException();
        }
        Group group =groupOptional.get();
        Long adminId = group.getAdmin().getId();
        User user1 =user1Optional.get();
        User user2 =user2Optional.get();
        List<User> groupUserList = groupRepository.findByGroup(group.getId());
        if(!adminId.equals(user1.getId())){
            throw new User1NotAdminOfGroupException();
        }
        for(User user : groupUserList){
            if(user.getId().equals(user2.getId())){
                throw new UserAlreadyExitsException();
            }
        }
        groupUserList.add(user2);
        group.setUsers(groupUserList);
        return groupRepository.save(group);
    }

    public String findGroupsInvolved(Long userID) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userID);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        List<Group> groups = groupRepository.findAll();
        String ans ="";
        for(Group group : groups){
            for(User userU : group.getUsers()){
                if(userU.getId().equals(userID)) {
                    ans+= group.getName()+" ";
                    break;
                }
            }
        }
        return ans;
    }
}

package com.example.splitwise1.controllers;

import com.example.splitwise1.dtos.RegisterUserRequestDto;
import com.example.splitwise1.dtos.RegisterUserResponseDto;
import com.example.splitwise1.dtos.ResponseState;
import com.example.splitwise1.exceptions.UserAlreadyExitsException;
import com.example.splitwise1.models.User;
import com.example.splitwise1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto request) {
        RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
        User user;
        try{
            user = userService.registerUser(
                    request.getUserName(), request.getPassword(), request.getEmail()
            );
        }catch (UserAlreadyExitsException e){
            registerUserResponseDto.setResponseState(ResponseState.FAILURE);
            registerUserResponseDto.setMessage("User Already Exits");
            return registerUserResponseDto;
        }
        registerUserResponseDto.setResponseState(ResponseState.SUCCESS);
        registerUserResponseDto.setMessage("User Registered Successfully");
        return registerUserResponseDto;
    }
}

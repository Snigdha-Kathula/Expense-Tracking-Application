package com.example.splitwise1.commands;

import com.example.splitwise1.controllers.UserController;
import com.example.splitwise1.dtos.RegisterUserRequestDto;
import com.example.splitwise1.dtos.RegisterUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterCommand implements Command{
    UserController userController;
    @Autowired
    public RegisterCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String string) {
        String[] splitString = string.split(" ");
        return (splitString.length == 4 && splitString[0].equals(CommandKeywords.register));

    }

    @Override
    public void execute(String string) {
        String[] splitString = string.split(" ");
        String name = splitString[1];
        String password = splitString[2];
        String email = splitString[3];
        RegisterUserRequestDto request = new RegisterUserRequestDto();
        request.setPassword(password);
        request.setUserName(name);
        request.setEmail(email);
        RegisterUserResponseDto response = userController.registerUser(request);
        System.out.println(response.getMessage());

    }
}

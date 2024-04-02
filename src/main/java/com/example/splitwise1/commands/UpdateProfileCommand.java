package com.example.splitwise1.commands;

import com.example.splitwise1.controllers.ProfileController;
import com.example.splitwise1.dtos.UpdateProfileRequestDto;
import com.example.splitwise1.dtos.UpdateProfileResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateProfileCommand implements Command{
    ProfileController profileController;
    @Autowired
    public UpdateProfileCommand(ProfileController profileController) {
        this.profileController = profileController;
    }

    @Override
    public boolean matches(String input) {
        String[] split = input.split(" ");
        return (split.length == 3 && split[1].equals(CommandKeywords.updateProfile));
    }

    @Override
    public void execute(String input) {
        String[] split = input.split(" ");
        String email = split[0];
        String newPassword =split[2];
        UpdateProfileRequestDto request = new UpdateProfileRequestDto();
        request.setEmail(email);
        request.setNewPassword(newPassword);
        UpdateProfileResponseDto response = profileController.updateProfile(request);
        System.out.println(response.getMessage());

    }
}

package com.example.splitwise1.controllers;

import com.example.splitwise1.dtos.ResponseState;
import com.example.splitwise1.dtos.UpdateProfileRequestDto;
import com.example.splitwise1.dtos.UpdateProfileResponseDto;
import com.example.splitwise1.exceptions.UserNotFoundException;
import com.example.splitwise1.models.User;
import com.example.splitwise1.services.UpdateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProfileController {
    UpdateProfileService updateProfileService;
    @Autowired
    public ProfileController(UpdateProfileService updateProfileService) {
        this.updateProfileService = updateProfileService;
    }

    public UpdateProfileResponseDto updateProfile(UpdateProfileRequestDto request){
        UpdateProfileResponseDto updateProfileResponseDto = new UpdateProfileResponseDto();
        User user;
        try{
            user=updateProfileService.updateProfile(
                    request.getEmail(), request.getNewPassword()
            );
        }catch (UserNotFoundException unfe){
            updateProfileResponseDto.setMessage("User Not Found : Entered Email is Incorrect, Check it out");
            updateProfileResponseDto.setResponseState(ResponseState.FAILURE);
            return updateProfileResponseDto;
        }
        updateProfileResponseDto.setMessage("User Password Has been UpDated :");
        updateProfileResponseDto.setResponseState(ResponseState.SUCCESS);
        return updateProfileResponseDto;

    }
}

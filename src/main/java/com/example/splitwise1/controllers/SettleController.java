package com.example.splitwise1.controllers;

import com.example.splitwise1.dtos.*;
import com.example.splitwise1.exceptions.GroupNotFoundException;
import com.example.splitwise1.exceptions.UserNotFoundException;
import com.example.splitwise1.services.SettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SettleController {
    SettleService settleService;
    @Autowired
    public SettleController(SettleService settleService) {
        this.settleService = settleService;
    }

    public TotalResponseDto myTotal(TotalRequestDto request) {
        TotalResponseDto  response = new TotalResponseDto();
        Long amount;
        try{
            amount = settleService.myTotal(request.getUserId());
        }catch (UserNotFoundException e){
            response.setMessage("User Not found Exception: Enter valid userId(registered userId)");
            response.setResponseState(ResponseState.FAILURE);
            return response;
        }catch (RuntimeException e){
            response.setMessage("RunTime");
            response.setResponseState(ResponseState.FAILURE);
            return response;
        }
        response.setMessage("Successfully fetched : MyTotal = "+amount+" \n"+"\nIf printed Amount is positive : That's what he want to get(receive) from Others.\n"+"If printed Amount is negative : That's what he want to pay(give) to Others\n");
        response.setAmount(amount);
        response.setResponseState(ResponseState.SUCCESS);
        return response;
    }

    public SettleUpResponseDto settleUpUser(SettleUpRequestDto request) {
        SettleUpResponseDto settleUpResponseDto = new SettleUpResponseDto();
        try{
            settleService.settleUpUser(request.getId());
        }catch (UserNotFoundException e){
            settleUpResponseDto.setResponseState(ResponseState.FAILURE);
            settleUpResponseDto.setMessage("Entered User Id was wrong");
            return settleUpResponseDto;
        }
        settleUpResponseDto.setResponseState(ResponseState.SUCCESS);
        settleUpResponseDto.setMessage(request.getId()+" needs to Settle these Tranactions.");
        return settleUpResponseDto;
    }

    public SettleUpResponseDto settleUpGroup(SettleUpGroupRequestDto request) {
        SettleUpResponseDto settleUpResponseDto = new SettleUpResponseDto();
        try{
            settleService.settleUpGroup(request.getUserId(), request.getGrpId());
        }catch (UserNotFoundException e){
            settleUpResponseDto.setResponseState(ResponseState.FAILURE);
            settleUpResponseDto.setMessage("Entered User Id was Not Found");
            return settleUpResponseDto;
        }catch (GroupNotFoundException e){
            settleUpResponseDto.setResponseState(ResponseState.FAILURE);
            settleUpResponseDto.setMessage("Entered Group Id was Not Found");
            return settleUpResponseDto;
        }
        settleUpResponseDto.setResponseState(ResponseState.SUCCESS);
        settleUpResponseDto.setMessage(request.getGrpId()+" need to Settle these Tranactions.");
        return settleUpResponseDto;
    }
}

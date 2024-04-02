package com.example.splitwise1.controllers;

import com.example.splitwise1.dtos.CommonResponseDto;
import com.example.splitwise1.dtos.ExpenseRequestDto;
import com.example.splitwise1.dtos.ResponseState;
import com.example.splitwise1.exceptions.*;
import com.example.splitwise1.services.ServiceExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ExpenseController {
    ServiceExpense serviceExpense;
    @Autowired
    public ExpenseController(ServiceExpense executorService) {
        this.serviceExpense = executorService;
    }

    public CommonResponseDto createExpense(ExpenseRequestDto request) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        try{
            serviceExpense.createExpense(
                    request.getUserIds(), request.getGrpId(), request.getPaid(), request.getSplitword(), request.getHadToPay(), request.getDesc()
            );
        }catch (UserNotFoundException e){
            commonResponseDto.setResponseState(ResponseState.FAILURE);
            commonResponseDto.setMessage("Users not Found:(Reason -> user NotRegistered");
            return commonResponseDto;
        }catch (GroupNotFoundException e){
            commonResponseDto.setResponseState(ResponseState.FAILURE);
            commonResponseDto.setMessage("Group not Found:Group with provided Id, does not exist.");
            return commonResponseDto;
        }catch (UserNotPartOfGroupException e){
            commonResponseDto.setResponseState(ResponseState.FAILURE);
            commonResponseDto.setMessage("User not Found:Expense Creator is not part of Group");
            return commonResponseDto;
        }catch (InputWrongException e){
            commonResponseDto.setResponseState(ResponseState.FAILURE);
            commonResponseDto.setMessage("Sum of paid isNotEqualTo sum of hAsToPAy \n PaidList.size should not be > userList.size \n hasToPayList.size should not be > userList.size");
            return commonResponseDto;
        }
        commonResponseDto.setResponseState(ResponseState.SUCCESS);
        commonResponseDto.setMessage("SuccessFully Expense is Created.");
        return commonResponseDto;
    }


}

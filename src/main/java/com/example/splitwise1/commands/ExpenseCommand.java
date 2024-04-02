package com.example.splitwise1.commands;
import com.example.splitwise1.controllers.ExpenseController;
import com.example.splitwise1.dtos.CommonResponseDto;
import com.example.splitwise1.dtos.ExpenseRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ExpenseCommand implements Command{
    ExpenseController expenseController;
    @Autowired
    public ExpenseCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        String[] split =input.split(" ");
        return (split.length >= 6 && split[1].equals(CommandKeywords.expense));
    }

    @Override
    public void execute(String input) {
        String[] inpt = input.split(" ");
        List<String> splitWords = new ArrayList<>();
        splitWords.add("Equal");
        splitWords.add("Exact");
        splitWords.add("Ratio");
        splitWords.add("Percent");
        List<Long> userIds = new ArrayList<>();
        Long grpId=null;
        int ind=0;
        for(int i=0;i<inpt.length;i++){
            if(inpt[i].equals("iPay")|| inpt[i].equals("MultiPay")){
                ind =i+1;
                break;
            }else if (i!=1 && inpt[i].charAt(0)=='u'){
                userIds.add(Long.parseLong(inpt[i].substring(1)));
            }else if (i!=1 && inpt[i].charAt(0)=='g'){
                grpId = Long.parseLong(inpt[i].substring(1));
            }
        }
        String splitword = "";
        List<Long> paid =new ArrayList<>();
        for(int i=ind;i<inpt.length;i++){
            if(splitWords.contains(inpt[i])){
                splitword = inpt[i];
                ind =i+1;
                break;
            }else{
                paid.add(Long.parseLong(inpt[i]));
            }
        }
        List<Long> hadToPay =new ArrayList<>();
        for(int i=ind;i<inpt.length;i++){
            if(inpt[i].equals("Desc")){
                ind =i+1;
                break;
            }else{
                hadToPay.add(Long.parseLong(inpt[i]));
            }
        }
        String desc = "";
        for(int i=ind;i<inpt.length;i++){
             desc += inpt[i]+" ";
        }
//        System.out.println(userIds);
//        System.out.println(grpId);
//        System.out.println(paid);
//        System.out.println(hadToPay);
//        System.out.println(desc);
        ExpenseRequestDto request = new ExpenseRequestDto();
        request.setUserIds(userIds);
        request.setGrpId(grpId);
        request.setPaid(paid);
        request.setSplitword(splitword);
        request.setHadToPay(hadToPay);
        request.setDesc(desc);
        CommonResponseDto response = expenseController.createExpense(request);
        System.out.println(response.getMessage());
    }

}

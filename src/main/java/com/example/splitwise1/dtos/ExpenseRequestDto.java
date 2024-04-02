package com.example.splitwise1.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ExpenseRequestDto {

    private List<Long> userIds;
    private Long grpId;
    private List<Long> paid ;
    private String splitword;
    private List<Long> hadToPay;
    private String desc;
    //        System.out.println(userIds);
//        System.out.println(grpId);
//        System.out.println(paid);
//        System.out.println(hadToPay);
//        System.out.println(desc);
}

package com.example.splitwise1.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettleUpResponseDto {
    private String message;
    private ResponseState responseState;
}

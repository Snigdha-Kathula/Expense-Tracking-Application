package com.example.splitwise1.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequestDto {
    private String userName;
    private String email;
    private String password;

}

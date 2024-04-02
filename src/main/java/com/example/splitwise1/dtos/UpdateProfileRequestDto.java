package com.example.splitwise1.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequestDto {
    private String email;
    private String newPassword;

}

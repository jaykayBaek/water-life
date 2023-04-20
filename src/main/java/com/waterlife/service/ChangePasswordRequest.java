package com.waterlife.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordRequest {
    private String password;
    private String passwordNew;
    private String passwordConfirm;
}

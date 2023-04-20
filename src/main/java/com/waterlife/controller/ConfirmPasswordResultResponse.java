package com.waterlife.controller;

import lombok.Getter;

@Getter
public class ConfirmPasswordResultResponse extends ResultResponse {
    private final String maskedLoginId;

    public ConfirmPasswordResultResponse(String CODE, String MESSAGE, boolean SUCCESS, String maskedLoginId) {
        super(CODE, MESSAGE, SUCCESS);
        this.maskedLoginId = maskedLoginId;
    }
}

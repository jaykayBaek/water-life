package com.waterlife.controller;

import lombok.Getter;

@Getter
public class FindLoginIdResultResponse extends ResultResponse {
    private final String maskedLoginId;

    public FindLoginIdResultResponse(String CODE, String MESSAGE, boolean SUCCESS, String maskedLoginId) {
        super(CODE, MESSAGE, SUCCESS);
        this.maskedLoginId = maskedLoginId;
    }
}

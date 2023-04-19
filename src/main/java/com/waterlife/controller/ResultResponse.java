package com.waterlife.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResultResponse {
    private final String CODE;
    private final String MESSAGE;
    private final boolean SUCCESS;
}

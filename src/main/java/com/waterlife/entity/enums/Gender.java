package com.waterlife.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum Gender {
    MEN("남성"), WOMEN("여성"), SECRET("비공개");
    
    private final String description;
}

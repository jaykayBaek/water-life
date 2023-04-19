package com.waterlife.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Ranking {
    BRONZE("브론즈"),
    SILVER("실버"),
    GOLD("골드"),
    PLATINUM("플래티넘"),
    DIAMOND("다이아몬드");

    private final String description;
}

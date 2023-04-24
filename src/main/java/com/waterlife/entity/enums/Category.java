package com.waterlife.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    GENERAL("일반"), QUESTION("질문"), VISIT("방문기"),
    SHARE("분양"), AQUARIUM_PICTURE("어항 사진"), CONTEST("콘테스트");

    private final String description;
}

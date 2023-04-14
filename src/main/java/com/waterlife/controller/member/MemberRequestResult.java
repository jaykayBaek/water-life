package com.waterlife.controller.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberRequestResult {
    REGISTER_SUCCESS("정상적으로 회원가입되었습니다.");
    private final String message;
}

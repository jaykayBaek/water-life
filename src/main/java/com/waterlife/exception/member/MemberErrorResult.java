package com.waterlife.exception.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum MemberErrorResult {
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "이미 존재하거나 회원 탈퇴한 아이디입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 서로 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}

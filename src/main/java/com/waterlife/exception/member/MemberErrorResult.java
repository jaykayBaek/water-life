package com.waterlife.exception.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum MemberErrorResult {
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "이미 존재하거나 회원 탈퇴한 아이디입니다."),
    LOGIN_INFO_NOT_MATCH(HttpStatus.BAD_REQUEST, "회원가입 되지 않은 아이디이거나, 아이디나 비밀번호를 잘못 입력했습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");
    private final HttpStatus status;
    private final String message;
}

package com.waterlife.exception.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum MemberErrorResult {
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "이미 존재하거나 회원 탈퇴한 아이디입니다."),
    LOGIN_INFO_NOT_MATCH(HttpStatus.BAD_REQUEST, "회원가입 되지 않은 아이디이거나, 아이디나 비밀번호를 잘못 입력했습니다."),
    MEMBER_NOT_FOUND_BY_FIND_MEMBER_ID(HttpStatus.NOT_FOUND, "일치하는 회원정보가 없습니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다."),
    MEMBER_NOT_FOUND_BY_FIND_LOGIN_ID(HttpStatus.NOT_FOUND, "일치하는 회원정보가 없습니다. 이메일을 다시 확인해주세요."),
    MEMBER_NOT_FOUND_BY_FIND_PASSWORD(HttpStatus.NOT_FOUND, "일치하는 회원정보가 없습니다. 아이디 또는 이메일 주소를 다시 확인해주세요.");
    private final HttpStatus status;
    private final String message;
}

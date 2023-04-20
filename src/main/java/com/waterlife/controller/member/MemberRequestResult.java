package com.waterlife.controller.member;

import com.sun.mail.imap.IMAPFolder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberRequestResult {
    REGISTER_SUCCESS("정상적으로 회원가입되었습니다."),
    NOT_DUPLICATED_LOGIN_ID("중복된 아이디가 없습니다."),
    NOT_DUPLICATED_EMAIL("중복된 이메일이 없습니다."),
    FIND_LOGIN_ID_SUCCESS("아이디 찾기 성공"),
    FIND_PASSWORD_SUCCESS("비밀번호 찾기 성공"),
    CONFIRM_PASSWORD_SUCCESS("비밀번호 확인 성공"),
    CHANGE_SUCCESS_PASSWORD("비밀번호 변경이 완료되었습니다."),
    CHANGE_SUCCESS_NICKNAME("닉네임 변경이 완료되었습니다."),
    CHANGE_SUCCESS_EMAIL("이메일 변경이 완료되었습니다.")

    ;
    private final String message;
}

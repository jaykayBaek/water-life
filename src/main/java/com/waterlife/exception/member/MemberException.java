package com.waterlife.exception.member;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
    private MemberErrorResult errorResult;

    public MemberException(MemberErrorResult errorResult) {
        super(errorResult.getMessage());

        this.errorResult = errorResult;
    }
}

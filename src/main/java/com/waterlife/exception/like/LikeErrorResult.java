package com.waterlife.exception.like;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum LikeErrorResult {
    ALREADY_LIKES_THIS_POST(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 게시글입니다."),
    ALREADY_DISLIKES_THIS_POST(HttpStatus.BAD_REQUEST, "이미 싫어요를 누른 게시글입니다.")
    ;

    private final HttpStatus status;
    private final String message;
}

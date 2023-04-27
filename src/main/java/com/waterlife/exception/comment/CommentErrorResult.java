package com.waterlife.exception.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommentErrorResult {
    COMMENT_NOT_FOUND_BY_FIND_COMMENT_ID(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    NOT_MATCH_COMMENT_MEMBER_ID(HttpStatus.UNAUTHORIZED, "댓글 작성자와 회원님이 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}

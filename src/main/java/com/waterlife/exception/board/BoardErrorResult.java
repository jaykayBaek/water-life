package com.waterlife.exception.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum BoardErrorResult {
    BOARD_NOT_FOUND_BY_FIND_BOARD_ID(HttpStatus.NOT_FOUND, "검색한 게시글을 찾을 수 없습니다."),
    BOARD_NOT_FOUND_BY_COMMENT_ID(HttpStatus.NOT_FOUND, "해당하는 게시글을 찾을 수 없습니다."),
    NOT_COMMENTABLE(HttpStatus.BAD_REQUEST, "댓글을 작성할 수 없는 게시글입니다."),
    NOT_MATCH_BOARD_MEMBER_ID(HttpStatus.UNAUTHORIZED, "게시글 작성자와 회원님이 일치하지 않습니다."),
    ALREADY_DELETED_BOARD(HttpStatus.BAD_REQUEST, "이미 삭제된 게시글입니다.");
    private final HttpStatus status;
    private final String message;
}

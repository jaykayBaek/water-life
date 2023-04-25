package com.waterlife.exception.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum BoardErrorResult {
    BOARD_NOT_FOUND_BY_FIND_BOARD_ID(HttpStatus.NOT_FOUND, "검색한 게시글을 찾을 수 없습니다."),
    NOT_COMMENTABLE(HttpStatus.BAD_REQUEST, "댓글을 작성할 수 없는 게시글입니다.");
    private final HttpStatus status;
    private final String message;
}

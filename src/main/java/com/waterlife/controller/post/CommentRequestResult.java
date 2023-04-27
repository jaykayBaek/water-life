package com.waterlife.controller.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommentRequestResult {
    WRITE_SUCCESS("댓글 작성이 완료되었습니다."),
    UPDATE_SUCCESS("댓글 수정이 완료되었습니다."),
    DELETE_SUCCESS("댓글 삭제가 완료되었습니다.");
    private final String message;
}
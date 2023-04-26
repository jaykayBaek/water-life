package com.waterlife.exception.comment;

import lombok.Getter;

@Getter
public class CommentException extends RuntimeException {
    private CommentErrorResult errorResult;

    public CommentException(CommentErrorResult errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }
}

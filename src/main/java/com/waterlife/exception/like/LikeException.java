package com.waterlife.exception.like;

import lombok.Getter;

@Getter
public class LikeException extends RuntimeException {
    private LikeErrorResult errorResult;

    public LikeException(LikeErrorResult errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }
}

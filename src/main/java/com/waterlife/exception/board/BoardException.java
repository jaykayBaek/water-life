package com.waterlife.exception.board;

public class BoardException extends RuntimeException {
    private BoardErrorResult errorResult;

    public BoardException(BoardErrorResult errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }
}

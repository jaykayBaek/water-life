package com.waterlife.advisor.exception;

import com.waterlife.controller.ResultResponse;
import com.waterlife.controller.member.MemberRequestResult;
import com.waterlife.exception.like.LikeException;
import com.waterlife.exception.member.MemberException;
import com.waterlife.service.comment.CommentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestControllerAdvisor {
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ResultResponse> handleMemberException(MemberException e){
        log.warn("MemberException occur: ", e);

        ResultResponse resultResponse = new ResultResponse(
                e.getErrorResult().getStatus().toString(), e.getErrorResult().getMessage(), false
        );

        return ResponseEntity
                .status(e.getErrorResult().getStatus())
                .body(resultResponse);
    }

    @ExceptionHandler(LikeException.class)
    public ResponseEntity<ResultResponse> handleLikeException(LikeException e){
        log.warn("LikeException occur: ", e);

        ResultResponse resultResponse = new ResultResponse(
                e.getErrorResult().getStatus().toString(), e.getErrorResult().getMessage(), false
        );

        return ResponseEntity
                .status(e.getErrorResult().getStatus())
                .body(resultResponse);
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ResultResponse> handleCommentException(CommentException e){
        log.warn("CommentException occur: ", e);

        ResultResponse resultResponse = new ResultResponse(
                e.getErrorResult().getStatus().toString(), e.getErrorResult().getMessage(), false
        );

        return ResponseEntity
                .status(e.getErrorResult().getStatus())
                .body(resultResponse);
    }
}

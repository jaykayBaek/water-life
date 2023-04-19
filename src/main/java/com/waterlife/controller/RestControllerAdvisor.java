package com.waterlife.controller;

import com.waterlife.controller.member.MemberRequestResult;
import com.waterlife.exception.member.MemberException;
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
}

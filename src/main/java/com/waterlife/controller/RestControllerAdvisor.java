package com.waterlife.controller;

import com.waterlife.exception.member.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestControllerAdvisor {
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ResultResponse> handleMemberException(MemberException e){
        log.warn("MemberException occur: ", e);

        ResultResponse resultResponse = ResultResponse.builder()
                .CODE(e.getErrorResult().getStatus().toString())
                .MESSAGE(e.getErrorResult().getMessage())
                .SUCCESS(false)
                .build();

        return ResponseEntity
                .status(e.getErrorResult().getStatus())
                .body(resultResponse);
    }
}

package com.waterlife.controller.login;

import com.waterlife.controller.ResultResponse;
import com.waterlife.controller.member.MemberRequestResult;
import com.waterlife.service.LoginForm;
import com.waterlife.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(LoginForm form){

        memberService.login(form);

        ResultResponse resultResponse = ResultResponse.builder()
                .CODE(HttpStatus.OK.toString())
                .MESSAGE(MemberRequestResult.REGISTER_SUCCESS.getMessage())
                .SUCCESS(true)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }
}

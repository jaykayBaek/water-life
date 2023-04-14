package com.waterlife.controller.member;

import com.waterlife.controller.ResultResponse;
import com.waterlife.service.MemberRegisterForm;
import com.waterlife.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;
    @PostMapping("/register")
    public ResponseEntity<ResultResponse> register(MemberRegisterForm form){

        memberService.register(form);

        ResultResponse resultResponse = ResultResponse.builder()
                .CODE(HttpStatus.CREATED.toString())
                .MESSAGE(MemberRequestResult.REGISTER_SUCCESS.getMessage())
                .SUCCESS(true)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultResponse);
    }

}

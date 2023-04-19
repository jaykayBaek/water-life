package com.waterlife.controller.member;

import com.waterlife.controller.FindLoginIdResultResponse;
import com.waterlife.controller.ResultResponse;
import com.waterlife.service.MemberRegisterForm;
import com.waterlife.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<ResultResponse> register(
            @ModelAttribute MemberRegisterForm form, HttpServletRequest request){
        String lastVisitedIp = request.getRemoteAddr();
        form.updateLastVisitedIp(lastVisitedIp);

        memberService.register(form);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.CREATED.toString(), MemberRequestResult.REGISTER_SUCCESS.getMessage(), true
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultResponse);
    }

    @PostMapping("/register/check/loginId")
    public ResponseEntity<ResultResponse> checkLoginId(@RequestParam String loginId){
        memberService.validateLoginId(loginId);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), MemberRequestResult.NOT_DUPLICATED_LOGIN_ID.getMessage(), true
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultResponse);


    }

    @PostMapping("/register/check/email")
    public ResponseEntity<ResultResponse> checkEmail(@RequestParam String email){
        memberService.validateEmail(email);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), MemberRequestResult.NOT_DUPLICATED_EMAIL.getMessage(), true
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultResponse);
    }
}

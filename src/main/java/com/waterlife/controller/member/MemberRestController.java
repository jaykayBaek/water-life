package com.waterlife.controller.member;

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

        ResultResponse resultResponse = ResultResponse.builder()
                .CODE(HttpStatus.CREATED.toString())
                .MESSAGE(MemberRequestResult.REGISTER_SUCCESS.getMessage())
                .SUCCESS(true)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultResponse);
    }

    @PostMapping("/register/check/loginId")
    public ResponseEntity<ResultResponse> checkLoginId(@RequestParam String loginId){
        memberService.validateLoginId(loginId);

        ResultResponse resultResponse = ResultResponse.builder()
                .CODE(HttpStatus.OK.toString())
                .MESSAGE(MemberRequestResult.NOT_DUPLICATED_LOGIN_ID.getMessage())
                .SUCCESS(true)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultResponse);
    }

}

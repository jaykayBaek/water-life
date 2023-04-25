package com.waterlife.controller.login;

import com.waterlife.consts.SessionConst;
import com.waterlife.controller.ResultResponse;
import com.waterlife.controller.member.MemberRequestResult;
import com.waterlife.entity.Member;
import com.waterlife.service.member.LoginForm;
import com.waterlife.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<ResultResponse> login(@ModelAttribute LoginForm form,
                                                HttpServletRequest request, HttpServletResponse response){
        log.info("login form = {}", form);

        Member member = memberService.login(form);

        manageCookieAccordingToBoolean(form, response);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.MEMBER_ID, member.getId());

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), MemberRequestResult.REGISTER_SUCCESS.getMessage(), true
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }

    @PostMapping("/members/logout")
    @ResponseBody
    public ResponseEntity logout(HttpSession session){
        session.invalidate();

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), "로그아웃 완료", true
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }

    private static void manageCookieAccordingToBoolean(LoginForm form, HttpServletResponse response) {
        if(form.isLoginIdRem()){
            Cookie idCookie = new Cookie("loginId", form.getLoginId());
            response.addCookie(idCookie);
        } else{
            Cookie idCookie = new Cookie("loginId", null);
            idCookie.setMaxAge(0);
            response.addCookie(idCookie);
        }
    }
}

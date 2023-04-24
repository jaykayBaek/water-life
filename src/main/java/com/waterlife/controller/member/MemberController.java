package com.waterlife.controller.member;

import com.waterlife.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/register")
    public String registerForm(){
        return "member/signup";
    }

    @GetMapping("/finds/id")
    public String findLoginId(){
        return "member/findsId";
    }

    @GetMapping("/finds/password")
    public String findPassword(){
        return "member/findsPassword";
    }
}
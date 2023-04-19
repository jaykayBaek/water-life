package com.waterlife.controller;

import com.waterlife.consts.SessionConst;
import com.waterlife.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId, Model model){
        if(memberId != null){
            MemberInformationResponse response = memberService.findMemberInformation(memberId);
            model.addAttribute("member", response);
        }

        return "home/home";
    }
}

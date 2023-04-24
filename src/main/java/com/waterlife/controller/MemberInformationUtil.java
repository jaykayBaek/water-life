package com.waterlife.controller;

import com.waterlife.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberInformationUtil {
    private final MemberService memberService;

    public void getMemberInformation(Long memberId, Model model) {
        if(memberId != null){
            MemberInformationResponse response = memberService.findMemberInformation(memberId);
            model.addAttribute("member", response);
        }
    }
}

package com.waterlife.service;

import com.waterlife.entity.Member;
import com.waterlife.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void register(MemberRegisterDto memberDto){
        Member member = Member.builder()
                .build();
        memberRepository.save(member);
    }
}

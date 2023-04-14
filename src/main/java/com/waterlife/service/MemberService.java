package com.waterlife.service;

import com.waterlife.entity.Member;
import com.waterlife.entity.enums.Ranking;
import com.waterlife.exception.member.MemberErrorResult;
import com.waterlife.exception.member.MemberException;
import com.waterlife.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long register(MemberRegisterForm form){
        validateForm(form);
        Member member = Member.createMember(form);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public void validateForm(MemberRegisterForm form) {
        String loginId = form.getLoginId();
        Optional<Member> findMemberByLoginId = memberRepository.findByLoginId(loginId);

        if(findMemberByLoginId.isPresent()){
            throw new MemberException(MemberErrorResult.DUPLICATED_LOGIN_ID);
        }

        if(isPasswordNotMatch(form)){
            throw new MemberException(MemberErrorResult.PASSWORD_NOT_MATCH);
        }

    }

    private static boolean isPasswordNotMatch(MemberRegisterForm form) {
        return !form.getPassword().equals(form.getPasswordConfirm());
    }

}
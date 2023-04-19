package com.waterlife.service;

import com.waterlife.controller.MemberInformationResponse;
import com.waterlife.entity.Member;
import com.waterlife.exception.member.MemberErrorResult;
import com.waterlife.exception.member.MemberException;
import com.waterlife.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long register(MemberRegisterForm form){
        validateForm(form);

        passwordEncode(form);

        Member member = Member.createMember(form);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public Member login(LoginForm form){
        String loginId = form.getLoginId();

        Member findMember = memberRepository.findByLoginId(loginId)
                .orElseThrow(()->new MemberException(MemberErrorResult.LOGIN_INFO_NOT_MATCH));

        boolean result = passwordEncoder.matches(form.getPassword(), findMember.getPassword());

        if(result == false){
            throw new MemberException(MemberErrorResult.LOGIN_INFO_NOT_MATCH);
        }

        return findMember;
    }

    private void passwordEncode(MemberRegisterForm form) {
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        form.encodePassword(encodedPassword);
    }

    public void validateForm(MemberRegisterForm form) {
        String loginId = form.getLoginId();
        String email = form.getEmail();

        validateLoginId(loginId);
        validateEmail(email);

        if(isPasswordNotMatch(form)){
            throw new MemberException(MemberErrorResult.LOGIN_INFO_NOT_MATCH);
        }

    }

    private static boolean isPasswordNotMatch(MemberRegisterForm form) {
        return !form.getPassword().equals(form.getPasswordConfirm());
    }

    public void validateLoginId(String loginId) {
        Optional<Member> findMemberByLoginId = memberRepository.findByLoginId(loginId);

        if(findMemberByLoginId.isPresent()){
            throw new MemberException(MemberErrorResult.DUPLICATED_LOGIN_ID);
        }
    }

    public void validateEmail(String email) {
        Optional<Member> findMemberByEmail = memberRepository.findByEmail(email);

        if(findMemberByEmail.isPresent()){
            throw new MemberException(MemberErrorResult.DUPLICATED_EMAIL);
        }
    }

    public MemberInformationResponse findMemberInformation(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorResult.MEMBER_NOT_FOUND));
        return MemberInformationResponse.createResponse(member);
    }
}
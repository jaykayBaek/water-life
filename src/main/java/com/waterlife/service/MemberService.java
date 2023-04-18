package com.waterlife.service;

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

    public void login(LoginForm form){
        String loginId = form.getLoginId();

        Member findMember = memberRepository.findByLoginId(loginId)
                .orElseThrow(()->new MemberException(MemberErrorResult.LOGIN_INFO_NOT_MATCH));

        boolean result = passwordEncoder.matches(form.getPassword(), findMember.getPassword());

        if(result == false){
            throw new MemberException(MemberErrorResult.LOGIN_INFO_NOT_MATCH);
        }
    }

    private void passwordEncode(MemberRegisterForm form) {
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        form.encodePassword(encodedPassword);
    }

    public void validateForm(MemberRegisterForm form) {
        String loginId = form.getLoginId();

        validateLoginId(loginId);

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
}
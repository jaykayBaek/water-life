package com.waterlife.service;

import com.waterlife.entity.Member;
import com.waterlife.exception.member.MemberErrorResult;
import com.waterlife.exception.member.MemberException;
import com.waterlife.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void register_fail_duplicated_loginId() {
        //given
        MemberRegisterForm form = new MemberRegisterForm();
        form.setLoginId("test");
        Member member = Member.createMember(form);
        memberRepository.save(member);

        //when
        MemberRegisterForm test = new MemberRegisterForm();
        test.setLoginId("test");

        //then
        assertThatThrownBy(()-> memberService.validateForm(test))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining(MemberErrorResult.DUPLICATED_LOGIN_ID.getMessage());

    }

    @Test
    void register_fail_unmatched_password() {
        //given
        MemberRegisterForm form = new MemberRegisterForm();
        form.setLoginId("test");
        form.setPassword("1234");
        form.setPasswordConfirm("4567");

        //then
        assertThatThrownBy(()-> memberService.validateForm(form))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining(MemberErrorResult.PASSWORD_NOT_MATCH.getMessage());
    }

    @Test
    void register_success() {
        //given
        MemberRegisterForm form = new MemberRegisterForm();
        form.setLoginId("test");
        form.setPassword("1234");
        form.setPasswordConfirm("1234");

        //when
        memberService.register(form);

        //then
        Member findMember = memberRepository.findByLoginId(form.getLoginId()).get();
        assertThat(findMember.getLoginId()).isEqualTo(form.getLoginId());
        assertThat(findMember.getPassword()).isEqualTo(form.getPassword());

    }
}
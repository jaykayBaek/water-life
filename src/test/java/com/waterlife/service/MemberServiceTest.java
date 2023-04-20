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
                .hasMessageContaining(MemberErrorResult.LOGIN_INFO_NOT_MATCH.getMessage());
    }

    @Test
    void register_fail_duplicate_email() {
        //given
        MemberRegisterForm form1 = new MemberRegisterForm();
        form1.setLoginId("test1");
        form1.setPassword("1234");
        form1.setPasswordConfirm("1234");
        form1.setEmail("test");

        Member member1 = Member.createMember(form1);
        memberRepository.save(member1);

        //when
        MemberRegisterForm form2 = new MemberRegisterForm();
        form2.setLoginId("test2");
        form2.setPassword("1234");
        form2.setPasswordConfirm("1234");
        form2.setEmail("test");

        //then
        assertThatThrownBy(()-> memberService.register(form2))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining(MemberErrorResult.DUPLICATED_EMAIL.getMessage());

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

    @Test
    void login_fail_loginId_not_match() {
        //given
        MemberRegisterForm form = new MemberRegisterForm();
        form.setLoginId("test");
        form.setPassword("1234");
        form.setPasswordConfirm("1234");

        memberService.register(form);

        //when
        LoginForm loginForm = new LoginForm();
        loginForm.setLoginId("test1");
        loginForm.setPassword("1234");

        assertThatThrownBy(()-> memberService.login(loginForm))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining(MemberErrorResult.LOGIN_INFO_NOT_MATCH.getMessage());

    }

    @Test
    void login_fail_password_not_match() {
        //given
        MemberRegisterForm form = new MemberRegisterForm();
        form.setLoginId("test");
        form.setPassword("1234");
        form.setPasswordConfirm("1234");

        memberService.register(form);

        //when
        LoginForm loginForm = new LoginForm();
        loginForm.setLoginId("test");
        loginForm.setPassword("12345");

        assertThatThrownBy(()-> memberService.login(loginForm))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining(MemberErrorResult.LOGIN_INFO_NOT_MATCH.getMessage());

    }

    @Test
    void login_id_find_fail_not_found() {
        //given
        MemberRegisterForm form = new MemberRegisterForm();
        form.setLoginId("test");
        form.setPassword("1234");
        form.setPasswordConfirm("1234");
        form.setEmail("test");

        memberService.register(form);

        //then
        String email = "hello";
        assertThatThrownBy(()-> memberService.findLoginId(email))
                .isInstanceOf(MemberException.class)
                .hasMessageContaining(MemberErrorResult.MEMBER_NOT_FOUND_BY_FIND_LOGIN_ID.getMessage());

    }
}
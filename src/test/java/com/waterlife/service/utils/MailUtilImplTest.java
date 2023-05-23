package com.waterlife.service.utils;

import com.waterlife.repository.MemberRepository;
import com.waterlife.service.member.MemberRegisterForm;
import com.waterlife.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@SpringBootTest
class MailUtilImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void passwordFindEmailSendTest(){
        MailUtil mailUtil = mock(MailUtil.class);
        MemberService memberService = new MemberService(memberRepository, passwordEncoder, mailUtil);

        MemberRegisterForm form = createMemberForm();
        memberService.register(form);
        String tempPassword = memberService.findPassword("test", "test@gmail");

        verify(mailUtil).passwordFindEmailSend("test@gmail", tempPassword);
    }

    private static MemberRegisterForm createMemberForm() {
        MemberRegisterForm form = new MemberRegisterForm();
        form.setLoginId("test");
        form.setPassword("test");
        form.setPasswordConfirm("test");
        form.setEmail("test@gmail");
        return form;
    }
}
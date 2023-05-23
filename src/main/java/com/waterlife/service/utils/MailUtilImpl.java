package com.waterlife.service.utils;

import com.waterlife.exception.email.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class MailUtilImpl implements MailUtil {
    private final JavaMailSender javaMailSender;

    /**
     * 이메일로 임시 비밀번호 전송하는 메소드
     * 테스트코드를 위해 tempPassword를 return함
     * @param toEmail
     * @param tempPassword
     * @return tempPassword
     */

    @Override
    public String passwordFindEmailSend(String toEmail, String tempPassword){
        MimeMessage message = createMessage(toEmail, tempPassword);
        javaMailSender.send(message);
        return tempPassword;
    }
    private MimeMessage createMessage(String toEmail, String tempPassword){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(toEmail);
            messageHelper.setSubject("[슬기로운 물생활] 비밀번호 찾기 결과입니다.");

            String sendMessage = "";
            sendMessage += "<h2>임시 비밀번호 재설정 결과</h2>";
            sendMessage += "<strong>임시 비밀번호 : </strong>";
            sendMessage += "<span>"+tempPassword+"</span>";
            message.setText(sendMessage, "utf-8", "html");

        } catch (MessagingException e) {
            throw new EmailException(e);
        }

        return message;
    }

}

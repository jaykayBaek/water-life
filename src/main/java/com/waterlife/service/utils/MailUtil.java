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
public class MailUtil {
    private final JavaMailSender javaMailSender;

    public void passwordFindEmailSend(String toEmail, String tempPassword){
        MimeMessage message = createMessage(toEmail, tempPassword);
        javaMailSender.send(message);
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

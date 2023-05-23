package com.waterlife.service.utils;

import javax.mail.internet.MimeMessage;

public interface MailUtil {
    public String passwordFindEmailSend(String toEmail, String tempPassword);
}

package com.waterlife.exception.email;

import javax.mail.MessagingException;

public class EmailException extends RuntimeException {
    public EmailException(MessagingException e) {
        super(e.getMessage());
    }
}

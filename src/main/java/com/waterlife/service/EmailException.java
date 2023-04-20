package com.waterlife.service;

import javax.mail.MessagingException;

public class EmailException extends RuntimeException {
    public EmailException(MessagingException e) {
        super(e.getMessage());
    }
}

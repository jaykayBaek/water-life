package com.waterlife.service.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class LoginForm {
    private String loginId;
    private String password;
    private boolean loginIdRem;
}

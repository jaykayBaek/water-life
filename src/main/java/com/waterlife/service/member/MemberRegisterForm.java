package com.waterlife.service.member;

import com.waterlife.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberRegisterForm {

    private String loginId;

    private String password;
    private String passwordConfirm;

    private String email;

    private String lastVisitedIp;

    private String memberName;

    private Integer birthYear;

    private Gender gender;
    private String phoneNo;

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateLastVisitedIp(String lastVisitedIp){
        this.lastVisitedIp = lastVisitedIp;
    }
}

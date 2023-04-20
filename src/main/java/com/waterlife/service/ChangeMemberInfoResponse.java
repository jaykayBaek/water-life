package com.waterlife.service;

import com.waterlife.entity.Member;
import lombok.Getter;

@Getter
public class ChangeMemberInfoResponse {
    private String memberName;
    private String loginId;
    private String email;
    private String nickname;
    private String phoneNo;
    private String ranking;
    private int levelPoint;
    private String gender;

    public static ChangeMemberInfoResponse createResponse(Member member){
        ChangeMemberInfoResponse response = new ChangeMemberInfoResponse();
        response.memberName = member.getMemberName();
        response.loginId = member.getLoginId();
        response.email = member.getEmail();
        response.nickname = member.getNickname();
        response.phoneNo = member.getPhoneNo();
        response.ranking = member.getRanking().getDescription();
        response.levelPoint = member.getLevelPoint();
        response.gender = member.getGender().getDescription();
        return response;
    }
}

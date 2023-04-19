package com.waterlife.controller;

import com.waterlife.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberInformationResponse {
    private LocalDateTime createdTime;
    private String nickname;
    private String ranking;

    public static MemberInformationResponse createResponse(Member member) {
        MemberInformationResponse response = new MemberInformationResponse();
        response.createdTime = member.getCreatedTime();
        response.nickname = member.getNickname();
        response.ranking = member.getRanking().getDescription();
        return response;
    }
}

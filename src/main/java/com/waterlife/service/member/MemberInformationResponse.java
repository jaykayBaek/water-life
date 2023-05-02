package com.waterlife.service.member;

import com.waterlife.entity.Member;
import com.waterlife.entity.enums.Ranking;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberInformationResponse {
    private LocalDateTime createdTime;
    private String nickname;
    private String ranking;
    private boolean isAdmin;

    public static MemberInformationResponse createResponse(Member member) {
        MemberInformationResponse response = new MemberInformationResponse();
        response.createdTime = member.getCreatedTime();
        response.nickname = member.getNickname();
        response.ranking = member.getRanking().getDescription();

        if(member.getRanking() == Ranking.ADMIN){
            response.isAdmin = true;
        } else{
            response.isAdmin = false;
        }

        return response;
    }
}

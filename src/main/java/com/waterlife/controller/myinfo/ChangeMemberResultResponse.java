package com.waterlife.controller.myinfo;

import com.waterlife.controller.ResultResponse;
import com.waterlife.service.member.ChangeMemberInfoResponse;
import lombok.Getter;

@Getter
public class ChangeMemberResultResponse extends ResultResponse {
    private final ChangeMemberInfoResponse response;

    public ChangeMemberResultResponse(String CODE, String MESSAGE, boolean SUCCESS, ChangeMemberInfoResponse response) {
        super(CODE, MESSAGE, SUCCESS);
        this.response = response;
    }
}

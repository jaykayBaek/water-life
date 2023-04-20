
package com.waterlife.controller.myinfo;

import com.waterlife.consts.SessionConst;
import com.waterlife.controller.ChangeMemberResultResponse;
import com.waterlife.controller.ResultResponse;
import com.waterlife.controller.member.MemberRequestResult;
import com.waterlife.service.ChangeMemberInfoResponse;
import com.waterlife.service.MemberService;
import com.waterlife.service.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyInfoRestController {

    private final MemberService memberService;

    @PostMapping("/my-info/confirm/password")
    public ResponseEntity<ResultResponse> confirmPassword(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId, String password){
        ChangeMemberInfoResponse response = memberService.confirmPasswordAndReturnMemberInfo(memberId, password);

        ChangeMemberResultResponse resultResponse = new ChangeMemberResultResponse(
                HttpStatus.OK.toString(), MemberRequestResult.CONFIRM_PASSWORD_SUCCESS.getMessage(), true,
                response
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }

    @PatchMapping("/my-info/password")
    public ResponseEntity<ResultResponse> updatePassword(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                                                         @ModelAttribute ChangePasswordRequest request){
        memberService.updatePassword(memberId, request);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), MemberRequestResult.CHANGE_SUCCESS_PASSWORD.getMessage(), true
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }

    @PatchMapping("my-info/nickname")
    public ResponseEntity<ResultResponse> updatePassword(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                                                         String nickname){
        memberService.updateNickname(memberId, nickname);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), MemberRequestResult.CHANGE_SUCCESS_PASSWORD.getMessage(), true
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }
}

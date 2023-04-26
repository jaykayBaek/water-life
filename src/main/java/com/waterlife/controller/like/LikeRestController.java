package com.waterlife.controller.like;

import com.waterlife.consts.SessionConst;
import com.waterlife.controller.ResultResponse;
import com.waterlife.controller.member.MemberRequestResult;
import com.waterlife.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeRestController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<ResultResponse> likePost(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                                                   Long boardId, Boolean isLike){
        likeService.likePost(memberId, boardId, isLike);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), "정상적으로 처리되었습니다.", true
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }
}

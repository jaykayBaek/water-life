package com.waterlife.controller.post;

import com.waterlife.consts.SessionConst;
import com.waterlife.controller.ResultResponse;
import com.waterlife.service.comment.CommentService;
import com.waterlife.service.comment.WriteCommentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentRestController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ResultResponse> writeComment(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                                                       WriteCommentRequest request){
        commentService.writeComment(memberId, request);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.CREATED.toString(), CommentRequestResult.WRITE_SUCCESS.getMessage(), true);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultResponse);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ResultResponse> updateComment(@PathVariable(name = "commentId") Long commentId,
                                                        @SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                                                        @RequestParam String content){
        commentService.updateComment(memberId, commentId, content);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), CommentRequestResult.UPDATE_SUCCESS.getMessage(), true);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }
}

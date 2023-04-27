package com.waterlife.controller.post;

import com.waterlife.consts.SessionConst;
import com.waterlife.controller.ResultResponse;
import com.waterlife.service.comment.NestedCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/nested-comments")
@RequiredArgsConstructor
@RestController
public class NestedCommentRestController {
    private final NestedCommentService nestedCommentService;

    @PostMapping
    public ResponseEntity<ResultResponse> writeNestedComment(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                                                             @ModelAttribute WriteNestedCommentRequest request){
        nestedCommentService.writeNestedComment(memberId, request.getBoardId(), request.getCommentId(), request.getContent());

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.CREATED.toString(), CommentRequestResult.WRITE_SUCCESS.getMessage(), true);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultResponse);
    }

    @PatchMapping("/{nestedCommentId}")
    public ResponseEntity<ResultResponse> updateNestedComment(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                                                              @PathVariable(name = "nestedCommentId") Long nestedCommentId, String content){
        nestedCommentService.updateNestedComment(memberId, nestedCommentId, content);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.CREATED.toString(), CommentRequestResult.WRITE_SUCCESS.getMessage(), true);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultResponse);
    }
}

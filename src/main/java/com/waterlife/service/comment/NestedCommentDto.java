package com.waterlife.service.comment;

import com.waterlife.entity.NestedComment;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class NestedCommentDto {
    private Long nestedCommentId;
    private Long boardId;
    private Long commentId;
    private Long memberId;
    private String nickname;
    private LocalDateTime createdTime;
    private String content;

    public NestedCommentDto(NestedComment comment){
        nestedCommentId = comment.getId();
        boardId = comment.getBoard().getId();
        commentId = comment.getComment().getId();
        memberId = comment.getMember().getId();
        nickname = comment.getMember().getNickname();
        createdTime = comment.getCreatedTime();
        content = comment.getContent();
    }
}

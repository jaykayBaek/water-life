package com.waterlife.service.comment;

import com.waterlife.entity.Comment;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
public class CommentDto {
    private Long commentId;
    private Long memberId;
    private String nickname;
    private LocalDateTime createdTime;
    private String content;
    private Boolean isDeleted;

    public CommentDto(Comment comment) {
        commentId = comment.getId();
        memberId = comment.getMember().getId();
        nickname = comment.getMember().getNickname();
        createdTime = comment.getCreatedTime();
        content = comment.getContent();
        isDeleted = comment.getIsDeleted();
    }
}

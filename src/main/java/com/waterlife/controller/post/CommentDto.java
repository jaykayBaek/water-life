package com.waterlife.controller.post;

import com.waterlife.entity.Comment;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
public class CommentDto {
    private Long memberId;
    private String nickname;
    private LocalDateTime createdTime;
    private String content;

    public CommentDto(Comment comment) {
        memberId = comment.getMember().getId();
        nickname = comment.getMember().getNickname();
        createdTime = comment.getCreatedTime();
        content = comment.getContent();
    }
}

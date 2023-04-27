package com.waterlife.service.comment;

import com.waterlife.entity.Comment;
import com.waterlife.entity.NestedComment;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@ToString
@Slf4j
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

        Optional<Long> optionalCommentId = Optional.ofNullable(comment.getComment())
                .map(Comment::getId);
        commentId = optionalCommentId.orElse(0L);

        memberId = comment.getMember().getId();
        nickname = comment.getMember().getNickname();
        createdTime = comment.getCreatedTime();
        content = comment.getContent();
    }
}

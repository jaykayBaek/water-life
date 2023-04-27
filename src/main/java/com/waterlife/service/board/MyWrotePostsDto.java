package com.waterlife.service.board;

import com.waterlife.entity.Board;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter @ToString
public class MyWrotePostsDto {
    private Long id;
    private String title;
    private int commentTotalCount;
    private LocalDateTime createdTime;
    private int views;
    private Boolean isDeleted;

    public MyWrotePostsDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.commentTotalCount = board.getCommentTotalCount();
        this.createdTime = board.getCreatedTime();
        this.views = board.getViews();
        this.isDeleted = board.getIsDeleted();
    }
}

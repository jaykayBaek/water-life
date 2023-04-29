package com.waterlife.controller;

import com.waterlife.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class HomeViewBoardDto {
    private Long id;
    private String title;
    private int commentTotalCount;
    private LocalDateTime createdTime;
    private int views;
    private int likes;
    private String nickname;

    public HomeViewBoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.commentTotalCount = board.getCommentTotalCount();
        this.createdTime = board.getCreatedTime();
        this.views = board.getViews();
        this.nickname = board.getMember().getNickname();
        this.likes = board.getLikes();
    }
}

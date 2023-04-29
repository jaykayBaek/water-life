package com.waterlife.repository;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString
public class SearchBoardDto {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private int commentTotalCount;
    private int views;
    private int likes;
    private LocalDateTime createdTime;

    @QueryProjection
    public SearchBoardDto(Long id, String title, String content, String nickname, int commentTotalCount, int views, int likes, LocalDateTime createdTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.commentTotalCount = commentTotalCount;
        this.views = views;
        this.likes = likes;
        this.createdTime = createdTime;
    }
}

package com.waterlife.entity;

import com.waterlife.service.board.WritePostRequest;
import com.waterlife.entity.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
public class Board extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<NestedComment> nestedComments = new ArrayList<>();

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Boolean commentable;
    private Boolean recommendable;

    private int views;
    private int likes;
    private int commentTotalCount;

    private Boolean isDeleted;

    public static Board createBoard(Member member, WritePostRequest request) {
        Board board = new Board();
        board.member = member;
        board.title = request.getTitle();
        board.content = request.getContent();
        board.category = request.getCategory();
        board.commentable = request.getCommentable();
        board.recommendable = false;
        board.views = 0;
        board.likes = 0;
        board.commentTotalCount = 0;
        board.isDeleted = false;
        return board;
    }

    public void updateViews(int updatedViews) {
        views = updatedViews;
    }

    public void updateCommentTotalCount(int updatedCommentTotalCount) {
        commentTotalCount = updatedCommentTotalCount;
    }

    public void updateLikes(int updatedLikes) {
        likes = updatedLikes;
    }

    public void updateRecommendable(boolean updatedRecommendable) {
        recommendable = updatedRecommendable;
    }

    public void updateBoard(WritePostRequest request) {
        title = request.getTitle();
        category = request.getCategory();
        content = request.getContent();
        commentable = request.getCommentable();
    }

    public void updateDeletedStatus(boolean status) {
        isDeleted = status;
    }
}
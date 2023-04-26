package com.waterlife.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class NestedComment extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "nested_comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private String content;

    public static NestedComment createComment(Member member, Board board, Comment comment, String content) {
        NestedComment nestedComment = new NestedComment();
        nestedComment.member = member;
        nestedComment.board = board;
        nestedComment.comment = comment;
        nestedComment.content = content;
        return nestedComment;
    }

    public void updateContent(String updatedContent) {
        content = updatedContent;
    }
}
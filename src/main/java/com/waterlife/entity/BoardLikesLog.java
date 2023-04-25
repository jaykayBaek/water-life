package com.waterlife.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BoardLikesLog extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_likes_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    /* --- 좋아요 true(1), 싫어요 false(0) 눌렀는지 --- */
    private Boolean isLikes;
    
    /* --- 생성 메소드 --- */
    public static BoardLikesLog createLikesLog(Member member, Board board, Boolean isLikes){
        BoardLikesLog log = new BoardLikesLog();
        log.member = member;
        log.board = board;
        log.isLikes = isLikes;
        return log;
    }
}

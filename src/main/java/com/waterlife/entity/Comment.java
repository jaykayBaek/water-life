package com.waterlife.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Comment extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String content;

    private int like;
    private int dislike;
}

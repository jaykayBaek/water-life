package com.waterlife.entity;

import lombok.Getter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Board extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Comment> Comments = new ArrayList<>();

    private String content;

    private int like;
    private int dislike;
}
package com.waterlife.entity;

import com.waterlife.entity.enums.Gender;
import com.waterlife.entity.enums.Ranking;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Slf4j
@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @Column(length = 20)
    private String loginId;

    private String password;

    private String email;

    private String lastVisitedIp;

    private String memberName;
    private String age;

    @Column(length = 4)
    private Integer birthYear;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNo;

    @Enumerated(EnumType.STRING)
    private Ranking ranking;
    private int levelPoint;
}
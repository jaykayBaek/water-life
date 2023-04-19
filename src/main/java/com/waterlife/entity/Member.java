package com.waterlife.entity;

import com.waterlife.entity.enums.Gender;
import com.waterlife.entity.enums.Ranking;
import com.waterlife.service.MemberRegisterForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
@Setter(PRIVATE)
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
    private String nickname;

    @Column(length = 4)
    private Integer birthYear;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNo;

    @Enumerated(EnumType.STRING)
    private Ranking ranking;
    private int levelPoint;

    /* --- 생성 메소드 --- */
    public static Member createMember(MemberRegisterForm form){
        Member member = new Member();
        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());
        member.setEmail(form.getEmail());
        member.setLastVisitedIp(form.getLastVisitedIp());
        member.setMemberName(form.getMemberName());
        member.setBirthYear(form.getBirthYear());
        member.setGender(form.getGender());
        member.setLastEditedTime(LocalDateTime.now());
        member.setPhoneNo(form.getPhoneNo());
        member.setNickname(form.getMemberName());
        member.setRanking(Ranking.BRONZE);
        member.setLevelPoint(0);
        return member;
    }

}
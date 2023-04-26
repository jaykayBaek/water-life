package com.waterlife.service.like;

import com.waterlife.consts.LikeConst;
import com.waterlife.entity.Board;
import com.waterlife.entity.Member;
import com.waterlife.entity.enums.Category;
import com.waterlife.exception.like.LikeErrorResult;
import com.waterlife.exception.like.LikeException;
import com.waterlife.service.board.BoardService;
import com.waterlife.service.board.WritePostRequest;
import com.waterlife.service.member.MemberRegisterForm;
import com.waterlife.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeServiceTest {
    @Autowired
    LikeService likeService;

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Test
    void fail_like_already_like_post() {
        //given
        Long memberId = saveMemberAndGetId("test");
        Long boardId = saveBoardAndGetId(memberId);

        likeService.likePost(memberId, boardId, true);

        //when
        assertThatThrownBy(()->
            likeService.likePost(memberId, boardId, true))
                .isInstanceOf(LikeException.class)
                .hasMessageContaining(LikeErrorResult.ALREADY_LIKES_THIS_POST.getMessage());

    }
    @Test
    void fail_lie_already_dislike_post() {
        //given
        Long memberId = saveMemberAndGetId("test");
        Long boardId = saveBoardAndGetId(memberId);

        likeService.likePost(memberId, boardId, false);

        //when
        assertThatThrownBy(()->
                        likeService.likePost(memberId, boardId, true))
                .isInstanceOf(LikeException.class)
                .hasMessageContaining(LikeErrorResult.ALREADY_DISLIKES_THIS_POST.getMessage());
    }

    @Test
    void success_like_post() {
        //given
        Long memberId = saveMemberAndGetId("test");
        Long boardId = saveBoardAndGetId(memberId);

        likeService.likePost(memberId, boardId, false);

        //when
        Board findBoard = boardService.findBoardByBoardId(boardId);
        int likes = findBoard.getLikes();

        //then
        assertThat(likes).isEqualTo(-1);
    }
    @Test
    void recommendable_isFalse() {
        //given
        Long firstMemberId = saveMemberAndGetId("test");
        Long boardId = saveBoardAndGetId(firstMemberId);

        //when
        for(int i = 0; i< LikeConst.RECOMMENDABLE_MINIMUM_LIKES; i++){
            Long memberId = saveMemberAndGetId("test"+i);
            likeService.likePost(memberId, boardId, true);
        }

        //then
        Board board = boardService.findBoardByBoardId(boardId);
        assertThat(board.getRecommendable()).isFalse();
    }

    @Test
    void success_recommendable_isTrue() {
        //given
        Long firstMemberId = saveMemberAndGetId("test");
        Long boardId = saveBoardAndGetId(firstMemberId);

        for(int i = 0; i<LikeConst.RECOMMENDABLE_MINIMUM_LIKES; i++){
            Long memberId = saveMemberAndGetId("test"+i);
            likeService.likePost(memberId, boardId, true);
        }

        //when
        Long memberId = saveMemberAndGetId("target");
        likeService.likePost(memberId, boardId, true);

        //then
        Board board = boardService.findBoardByBoardId(boardId);
        assertThat(board.getRecommendable()).isTrue();
    }

    private Long saveBoardAndGetId(Long memberId) {
        WritePostRequest request = new WritePostRequest();
        request.setTitle("test");
        request.setContent("test");
        request.setCategory(Category.GENERAL);
        request.setCommentable(true);
        Long boardId = boardService.writePost(memberId, request);
        return boardId;
    }

    private Long saveMemberAndGetId(String loginId) {
        MemberRegisterForm form = new MemberRegisterForm();
        form.setLoginId(loginId);
        form.setEmail(loginId);
        form.setPassword("1234");
        form.setPasswordConfirm("1234");
        Long memberId = memberService.register(form);
        return memberId;
    }
}
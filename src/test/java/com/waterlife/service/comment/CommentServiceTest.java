package com.waterlife.service.comment;

import com.waterlife.entity.Comment;
import com.waterlife.entity.enums.Category;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Test
    void fail_update_comment() {
        //given
        Long memberId = saveMemberAndGetId("test");
        Long boardId = saveBoardAndGetId(memberId);

        Long commentId = saveCommentAndGetId(memberId, boardId);

        //when
        Long targetId = saveMemberAndGetId("target");
        assertThatThrownBy(() ->
            commentService.updateComment(targetId, commentId, "change Test"))
                .isInstanceOf(CommentException.class)
                .hasMessageContaining(CommentErrorResult.NOT_MATCH_COMMENT_MEMBER_ID.getMessage());
    }

    @Test
    void success_update_comment() {
        //given
        Long memberId = saveMemberAndGetId("test");
        Long boardId = saveBoardAndGetId(memberId);

        Long commentId = saveCommentAndGetId(memberId, boardId);

        //when
        commentService.updateComment(memberId, commentId, "change Test");

        //then
        Comment comment = commentService.findCommentByCommentId(commentId);
        assertThat(comment.getContent()).isEqualTo("change Test");
    }

    private Long saveCommentAndGetId(Long memberId, Long boardId) {
        WriteCommentRequest request = new WriteCommentRequest();
        request.setBoardId(boardId);
        request.setContent("hello");
        Long commentId = commentService.writeComment(memberId, request);
        return commentId;
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
package com.waterlife.service.comment;

import com.waterlife.entity.Board;
import com.waterlife.entity.Comment;
import com.waterlife.entity.Member;
import com.waterlife.entity.NestedComment;
import com.waterlife.repository.NestedCommentRepository;
import com.waterlife.service.board.BoardService;
import com.waterlife.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NestedCommentService {
    private final NestedCommentRepository nestedCommentRepository;
    private final MemberService memberService;
    private final BoardService boardService;
    private final CommentService commentService;

    @Transactional
    public Long writeNestedComment(Long memberId, Long boardId, Long commentId, String content){
        Member member = memberService.findMemberByMemberId(memberId);
        Board board = boardService.findBoardByBoardId(boardId);
        Comment comment = commentService.findCommentByCommentId(commentId);

        NestedComment nestedComment = NestedComment.createComment(member, board, comment, content);

        int commentTotalCount = board.getCommentTotalCount();
        board.updateCommentTotalCount(++commentTotalCount);

        return nestedCommentRepository.save(nestedComment).getId();
    }

    public List<NestedCommentDto> findByBoardId(Long boardId) {
        List<NestedComment> commentList = nestedCommentRepository.findByBoardId(boardId);

        return commentList.stream()
                .map(comment -> new NestedCommentDto(comment))
                .collect(Collectors.toList());
    }
}

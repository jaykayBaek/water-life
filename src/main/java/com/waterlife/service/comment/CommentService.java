package com.waterlife.service.comment;

import com.waterlife.controller.post.CommentDto;
import com.waterlife.entity.Board;
import com.waterlife.entity.Comment;
import com.waterlife.entity.Member;
import com.waterlife.exception.board.BoardErrorResult;
import com.waterlife.exception.board.BoardException;
import com.waterlife.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    @Transactional
    public Long writeComment(Long memberId, WriteCommentRequest request){
        Member member = memberService.findMemberByMemberId(memberId);
        Board board = boardService.findBoardByBoardId(request.getBoardId());

        if(board.getCommentable() == false){
            throw new BoardException(BoardErrorResult.NOT_COMMENTABLE);
        }

        Comment comment = Comment.createComment(member, board, request.getContent());

        int commentTotalCount = board.getCommentTotalCount();
        board.updateCommentTotalCount(++commentTotalCount);
        return commentRepository.save(comment).getId();
    }

    public List<CommentDto> findByBoardId(Long boardId) {
        List<Comment> commentList = commentRepository.findByBoardId(boardId);

        return commentList.stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
    }
}
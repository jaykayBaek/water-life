package com.waterlife.service.board;

import com.waterlife.entity.Board;
import com.waterlife.entity.Member;
import com.waterlife.exception.board.BoardErrorResult;
import com.waterlife.exception.board.BoardException;
import com.waterlife.repository.BoardRepository;
import com.waterlife.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    /**
     * 게시글 작성 메소드
     * @param memberId
     * @param request
     */
    @Transactional
    public Long writePost(Long memberId, WritePostRequest request) {
        Member findMember = memberService.findMemberByMemberId(memberId);
        Board board = Board.createBoard(findMember, request);
        return boardRepository.save(board).getId();
    }

    /**
     * 게시글 열람 메소드
     * @param boardId
     */
    public BoardInformationResponse findBoardById(Long boardId) {
        Board board = findBoardByBoardId(boardId);
        return BoardInformationResponse.createResponse(board);
    }

    /**
     * 게시글 조회수 증가 메소드
     * @param boardId
     *
     */
    @Transactional
    public void addBoardViews(Long boardId) {
        Board board = findBoardByBoardId(boardId);

        int views = board.getViews();
        int updatedViews = ++views;

        board.updateViews(updatedViews);
    }

    /**
     * BoardId로 Board를 찾는 메소드
     * @param boardId
     * 게시글을 찾을 수 없을 시
     * @Throw BoardException(BOARD_NOT_FOUND_BY_FIND_BOARD_ID)
     */
    public Board findBoardByBoardId(Long boardId){
        if(boardId == null){
            throw new BoardException(BoardErrorResult.BOARD_NOT_FOUND_BY_FIND_BOARD_ID);
        }
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException(BoardErrorResult.BOARD_NOT_FOUND_BY_FIND_BOARD_ID));
    }

    public Page<MyWrotePostsDto> findMyWrotePosts(Long memberId, Pageable pageable) {
        Page<Board> findBoards = boardRepository.findMyWrotePosts(memberId, pageable);
        return findBoards.map(board -> new MyWrotePostsDto(board));
    }
}

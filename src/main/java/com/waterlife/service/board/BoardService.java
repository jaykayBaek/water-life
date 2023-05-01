package com.waterlife.service.board;

import com.waterlife.controller.HomeViewBoardDto;
import com.waterlife.controller.post.SearchCond;
import com.waterlife.entity.Board;
import com.waterlife.entity.Member;
import com.waterlife.entity.enums.Ranking;
import com.waterlife.exception.board.BoardErrorResult;
import com.waterlife.exception.board.BoardException;
import com.waterlife.repository.BoardRepository;
import com.waterlife.repository.SearchBoardDto;
import com.waterlife.service.member.MemberService;
import com.waterlife.service.utils.FileManageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final FileManageUtil fileManageUtil;

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
    public BoardInformationResponse findByBoardId(Long boardId) {
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
        return boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new BoardException(BoardErrorResult.BOARD_NOT_FOUND_BY_FIND_BOARD_ID));
    }

    public Page<MyWrotePostsDto> findMyWrotePosts(Long memberId, Pageable pageable) {
        Page<Board> findBoards = boardRepository.findMyWrotePosts(memberId, pageable);
        return findBoards.map(board -> new MyWrotePostsDto(board));
    }

    public Page<MyWrotePostsDto> findMyWroteComments(Long memberId, Pageable pageable) {
        Page<Board> findBoards = boardRepository.findMyWroteComments(memberId, pageable);
        return findBoards.map(board -> new MyWrotePostsDto(board));
    }

    @Transactional
    public void minusOneCommentTotalCount(Board board){
        int commentTotalCount = board.getCommentTotalCount();
        board.updateCommentTotalCount(--commentTotalCount);
    }

    @Transactional
    public void plusOneCommentTotalCount(Board board){
        int commentTotalCount = board.getCommentTotalCount();
        board.updateCommentTotalCount(++commentTotalCount);
    }

    public Board findBoardByCommentId(Long commentId) {
        return boardRepository.findByCommentId(commentId)
                .orElseThrow(() -> new BoardException(BoardErrorResult.BOARD_NOT_FOUND_BY_COMMENT_ID));

    }

    public Board findBoardByNestedCommentId(Long nestedCommentId) {
        return boardRepository.findByNestedCommentId(nestedCommentId)
                .orElseThrow(() -> new BoardException(BoardErrorResult.BOARD_NOT_FOUND_BY_COMMENT_ID));
    }

    @Transactional
    public void updatePost(Long memberId, Long boardId, WritePostRequest request) {
        Member member = memberService.findMemberByMemberId(memberId);
        Board board = findBoardByBoardId(boardId);

        fileManageUtil.deleteImagesAccordingToComparison(board.getContent(), request.getContent());

        validateBoardMemberId(member, board);
        board.updateBoard(request);
    }

    private void validateBoardMemberId(Member member, Board board) {
        if(board.getMember().getId() != member.getId()){
            throw new BoardException(BoardErrorResult.NOT_MATCH_BOARD_MEMBER_ID);
        }
    }

    public BoardModifyResponse modifyForm(Long memberId, Long boardId) {
        Member member = memberService.findMemberByMemberId(memberId);
        Board board = findBoardByBoardId(boardId);

        validateDeleteCheck(board);
        validateBoardMemberId(member, board);

        return BoardModifyResponse.createResponse(board);
    }

    private void validateDeleteCheck(Board board) {
        Boolean isDeleted = board.getIsDeleted();
        if(isDeleted == true){
            throw new BoardException(BoardErrorResult.ALREADY_DELETED_BOARD);
        }
    }

    @Transactional
    public void deleteOwnPost(Long memberId, Long boardId) {
        Member member = memberService.findMemberByMemberId(memberId);
        Board board = findBoardByBoardId(boardId);

        validateBoardMemberId(member, board);

        deletePost(board);
    }

    private void deletePost(Board board) {
        fileManageUtil.deleteImageInContent(board.getContent());
        board.updateDeletedStatus(true);
    }

    public List<HomeViewBoardDto> findFiveRecommendablePosts() {
        List<Board> board = boardRepository.findFiveRecommendablePosts();
        return board.stream()
                .map(b -> new HomeViewBoardDto(b))
                .collect(Collectors.toList());
    }

    public List<HomeViewBoardDto> findFiveQuestionPosts() {
        List<Board> board = boardRepository.findFiveQuestionPosts();
        return board.stream()
                .map(b -> new HomeViewBoardDto(b))
                .collect(Collectors.toList());
    }

    public List<HomeViewBoardDto> findFiveGeneralPosts() {
        List<Board> board = boardRepository.findFiveGeneralPosts();
        return board.stream()
                .map(b -> new HomeViewBoardDto(b))
                .collect(Collectors.toList());
    }

    public List<HomeViewBoardDto> findFiveNewPosts() {
        List<Board> board = boardRepository.findFiveNewPosts();
        return board.stream()
                .map(b -> new HomeViewBoardDto(b))
                .collect(Collectors.toList());
    }

    public Page<SearchBoardDto> getBoardSearchResult(SearchCond searchCond, Pageable pageable) {
        return boardRepository.getBoardSearchResult(searchCond, pageable);
    }

    @Transactional
    public void deletePostWithAdminPermission(Long memberId, Long boardId) {
        Member member = memberService.findMemberByMemberId(memberId);
        Board board = findBoardByBoardId(boardId);

        if(!member.getRanking().equals(Ranking.ADMIN)){
            throw new BoardException(BoardErrorResult.NOT_MATCH_BOARD_MEMBER_ID);
        }

        deletePost(board);
    }
}

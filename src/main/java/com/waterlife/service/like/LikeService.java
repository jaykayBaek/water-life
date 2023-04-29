package com.waterlife.service.like;

import com.waterlife.consts.LikeConst;
import com.waterlife.entity.Board;
import com.waterlife.entity.BoardLikesLog;
import com.waterlife.entity.Member;
import com.waterlife.exception.like.LikeErrorResult;
import com.waterlife.exception.like.LikeException;
import com.waterlife.repository.BoardLikesLogRepository;
import com.waterlife.repository.BoardRepositoryImpl;
import com.waterlife.service.board.BoardService;
import com.waterlife.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LikeService {
    private final BoardRepositoryImpl boardRepository;
    private final BoardLikesLogRepository boardLikesLogRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    @Transactional
    public void likePost(Long memberId, Long boardId, Boolean isLike){
        Member member = memberService.findMemberByMemberId(memberId);
        Board board = boardService.findBoardByBoardId(boardId);

        checkLikeLog(memberId, boardId);

        updateLikeAndCreateLikeLog(isLike, member, board);

        updateRecommendableIfOverMinimumLikes(board);
    }

    private static void updateRecommendableIfOverMinimumLikes(Board board) {
        Boolean recommendable = board.getRecommendable();
        if(recommendable == false){
            int likes = board.getLikes();
            if(likes > LikeConst.RECOMMENDABLE_MINIMUM_LIKES){
                board.updateRecommendable(true);
            }
        }
    }

    private void updateLikeAndCreateLikeLog(Boolean isLike, Member member, Board board) {
        BoardLikesLog log;
        if(isLike == true) {
            int likes = board.getLikes();
            board.updateLikes(++likes);
            log = BoardLikesLog.createLikesLog(member, board, true);
        } else{
            int likes = board.getLikes();
            board.updateLikes(--likes);
            log = BoardLikesLog.createLikesLog(member, board, false);
        }
        boardLikesLogRepository.save(log);
    }

    private void checkLikeLog(Long memberId, Long boardId) {
        Optional<BoardLikesLog> findLog = boardLikesLogRepository.findByMemberIdAndBoardId(memberId, boardId);

        if(findLog.isPresent()){
            Boolean isLikes = findLog.get().getIsLikes();
            if(isLikes == true){
                throw new LikeException(LikeErrorResult.ALREADY_LIKES_THIS_POST);
            } else{
                throw new LikeException(LikeErrorResult.ALREADY_DISLIKES_THIS_POST);
            }
        }
    }
}

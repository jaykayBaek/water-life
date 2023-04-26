package com.waterlife.repository;

import com.waterlife.entity.BoardLikesLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikesLogRepository extends JpaRepository<BoardLikesLog, Long> {
    Optional<BoardLikesLog> findByMemberIdAndBoardId(Long memberId, Long boardId);
}

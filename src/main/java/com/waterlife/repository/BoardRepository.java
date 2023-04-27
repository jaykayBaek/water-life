package com.waterlife.repository;

import com.waterlife.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b " +
            "join b.member m on m.id = :memberId " +
            "where b.isDeleted = false " +
            "order by b.id desc")
    Page<Board> findMyWrotePosts(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select b from Board b " +
            "join b.comments c " +
            "where c.member.id = :memberId and " +
            "c.isDeleted = false")
    Page<Board> findMyWroteComments(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select b from Board b " +
            "join b.comments c " +
            "where c.id = :commentId")
    Optional<Board> findByCommentId(@Param("commentId") Long commentId);
}

package com.waterlife.repository;

import com.waterlife.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b " +
            "join b.member m on m.id = :memberId " +
            "order by b.id desc")
    Page<Board> findMyWrotePosts(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select b from Board b " +
            "join b.comments c " +
            "where c.member.id = :memberId ")
    Page<Board> findMyWroteComments(@Param("memberId") Long memberId, Pageable pageable);

}

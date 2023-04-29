package com.waterlife.repository;

import com.waterlife.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c " +
            "join c.board b " +
            "where b.id = :boardId")
    List<Comment> findByBoardId(@Param("boardId") Long boardId);
}

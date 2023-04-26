package com.waterlife.repository;

import com.waterlife.entity.NestedComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NestedCommentRepository extends JpaRepository<NestedComment, Long> {
    List<NestedComment> findByBoardId(Long boardId);
}

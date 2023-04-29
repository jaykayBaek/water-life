package com.waterlife.repository;

import com.waterlife.entity.Board;

import java.util.List;

public interface HomeRepository {
    List<Board> findFiveRecommendablePosts();

    List<Board> findFiveQuestionPosts();

    List<Board> findFiveGeneralPosts();

    List<Board> findFiveNewPosts();
}

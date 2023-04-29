package com.waterlife.repository;

import com.waterlife.entity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class HomeRepositoryImpl implements HomeRepository {
    private final EntityManager em;


    @Override
    public List<Board> findFiveRecommendablePosts(){
        return em.createQuery(
                "select b from Board b " +
                "where b.recommendable = true and b.isDeleted = false " +
                        "order by b.id desc", Board.class)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public List<Board> findFiveQuestionPosts(){
        return em.createQuery(
                        "select b from Board b " +
                                "where b.category = com.waterlife.entity.enums.Category.QUESTION and b.isDeleted = false " +
                                "order by b.id desc", Board.class)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public List<Board> findFiveGeneralPosts(){
        return em.createQuery(
                        "select b from Board b " +
                                "where b.category = com.waterlife.entity.enums.Category.GENERAL and b.isDeleted = false " +
                                "order by b.id desc", Board.class)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public List<Board> findFiveNewPosts(){
        return em.createQuery(
                        "select b from Board b " +
                                "where b.isDeleted = false " +
                                "order by b.id desc", Board.class)
                .setMaxResults(5)
                .getResultList();
    }
}

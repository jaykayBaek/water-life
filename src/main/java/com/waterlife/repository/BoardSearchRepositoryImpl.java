package com.waterlife.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.waterlife.controller.post.SearchCond;
import com.waterlife.entity.Board;
import com.waterlife.entity.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.waterlife.entity.QBoard.*;
import static com.waterlife.entity.QMember.*;

public class BoardSearchRepositoryImpl implements BoardSearchRepository{

    private final JPAQueryFactory queryFactory;

    public BoardSearchRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<SearchBoardDto> getBoardSearchResult(SearchCond searchCond, Pageable pageable) {
        List<SearchBoardDto> content = queryFactory
                .select(new QSearchBoardDto(
                        board.id, board.title, board.content,
                        board.member.nickname, board.commentTotalCount,
                        board.views, board.likes, board.createdTime
                ))
                .from(board)
                .leftJoin(board.member, member)
                .where(
                        titleEq(searchCond.getQuery()),
                        categoryEq(searchCond.getCategory())
                )
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = queryFactory
                .select(Wildcard.count)
                .from(board)
                .leftJoin(board.member, member)
                .where(
                        titleEq(searchCond.getQuery()),
                        categoryEq(searchCond.getCategory())
                );
        return new PageImpl<SearchBoardDto>(content, pageable, total.fetchOne());
    }

    private BooleanExpression categoryEq(Category category) {
        return StringUtils.isEmpty(category) ? null : board.category.eq(category);
    }

    private BooleanExpression titleEq(String query) {
        return StringUtils.isEmpty(query) ? null : board.title.contains(query);
    }
}

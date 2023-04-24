package com.waterlife.service.board;

import com.waterlife.entity.Board;
import com.waterlife.entity.enums.Category;
import lombok.Getter;

@Getter
public class BoardInformationResponse {
    private Long boardId;
    private Long memberId;
    private String nickname;
    private String title;
    private String content;
    private Category category;
    private String categoryName;
    private Boolean commentable;
    private Boolean recommendable;
    private int likes;
    private int dislikes;
    private int views;
    private String createdTime;

    public static BoardInformationResponse createResponse(Board board){
        BoardInformationResponse response = new BoardInformationResponse();
        response.boardId = board.getId();
        response.memberId = board.getMember().getId();
        response.nickname = board.getMember().getNickname();
        response.title = board.getTitle();
        response.content = board.getContent();
        response.category = board.getCategory();
        response.categoryName = board.getCategory().getDescription();
        response.commentable = board.getCommentable();
        response.recommendable = board.getRecommendable();
        response.likes = board.getLikes();
        response.dislikes = board.getDislikes();
        response.views = board.getViews();
        response.createdTime = board.getCreatedTime().toString().substring(0, 10);
        return response;
    }
}

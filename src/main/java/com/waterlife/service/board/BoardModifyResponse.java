package com.waterlife.service.board;

import com.waterlife.entity.Board;
import com.waterlife.entity.enums.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class BoardModifyResponse {
    private Long boardId;
    private String title;
    private String content;
    private Category category;
    private String categoryName;
    private Boolean commentable;
    public static BoardModifyResponse createResponse(Board board) {
        BoardModifyResponse response = new BoardModifyResponse();
        response.boardId = board.getId();
        response.title = board.getTitle();
        response.content = board.getContent();
        response.category = board.getCategory();
        response.categoryName = board.getCategory().getDescription();
        response.commentable = board.getCommentable();
        return response;
    }
}

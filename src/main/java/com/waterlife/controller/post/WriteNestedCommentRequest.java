package com.waterlife.controller.post;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WriteNestedCommentRequest {
    private Long boardId;
    private Long commentId;
    private String content;
}

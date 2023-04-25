package com.waterlife.service.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WriteCommentRequest {
    private Long boardId;
    private String content;
}

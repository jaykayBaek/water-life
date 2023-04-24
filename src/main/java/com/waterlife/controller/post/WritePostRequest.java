package com.waterlife.controller.post;

import com.waterlife.entity.enums.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class WritePostRequest {
    private String title;
    private String content;
    private Category category;
    private Boolean commentable;
}

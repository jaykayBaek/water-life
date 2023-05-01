package com.waterlife.controller.post;

import com.waterlife.entity.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCond {
    private String query;
    private Category category;
    private Boolean isRecommendable;
}

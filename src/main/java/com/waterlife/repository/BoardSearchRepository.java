package com.waterlife.repository;

import com.waterlife.controller.HomeViewBoardDto;
import com.waterlife.controller.post.SearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearchRepository {
    Page<SearchBoardDto> getBoardSearchResult(SearchCond searchCond, Pageable pageable);
}

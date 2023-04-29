package com.waterlife.controller.post;

import com.waterlife.repository.SearchBoardDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PagingResponse {
    // 페이징 블록 사이즈(5칸)
    private int blockSize;

    //총 블럭수
    private int totalBlocks;

    public PagingResponse(Page<SearchBoardDto> boards) {
        blockSize = 5;
        totalBlocks = (int) Math.ceil(boards.getTotalPages() * 1.0 / blockSize);

    }
}

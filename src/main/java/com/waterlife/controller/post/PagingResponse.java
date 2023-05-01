package com.waterlife.controller.post;

import com.waterlife.repository.SearchBoardDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PagingResponse {
    // 페이징 블록 사이즈(5칸)
    private int blockSize;

    //총 블록 수
    private int totalBlocks;

    //현재 블록
    private int block;

    //현재 블록의 시작 페이지
    private int startPage;

    //현재 블록의 마지막 페이지
    private int lastPage;

    private int prevBlock;
    private int nextBlock;

    public PagingResponse(Page<SearchBoardDto> boards) {
        blockSize = 5;

        totalBlocks = (int) Math.ceil(boards.getTotalPages() * 1.0 / blockSize);

        block = (int) Math.ceil(boards.getPageable().getPageNumber()/blockSize);

        startPage = (block) * blockSize + 1;

        if(startPage == boards.getTotalPages()){
            lastPage = startPage;
        } else{
            lastPage = (startPage + blockSize - 1);
        }

        prevBlock = (block * blockSize) - blockSize;

        nextBlock = (block + 1) * blockSize;
    }
}

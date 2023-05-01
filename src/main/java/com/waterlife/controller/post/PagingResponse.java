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
    private int startPageInThisBlock;

    //현재 블록의 마지막 페이지
    private int lastPageInThisBlock;

    private int pageInPrevBlock;
    private int pageInNextBlock;

    public PagingResponse(Page<SearchBoardDto> boards) {
        /**
         * total 자료 건수 110건 일 때?
         * pageable size = 15
         * totalPage = 8
         * 현재 페이지 no = 6
         */
        blockSize = 5;
        
        // 총 블록 개수 ? 15 * 1.0 / 5 = 3 
        totalBlocks = (int) Math.ceil(boards.getTotalPages() * 1.0 / blockSize);
        
        // 현재 블록 ? 6/5 = 1
        block = (int) Math.ceil(boards.getPageable().getPageNumber()/blockSize);
        
        // 현재 블록에서 시작 페이지 ? 1 * 5 + 1 = 6
        // 사실은 5이지만, 유저 인터페이스에는 1부터 시작하므로 6임
        startPageInThisBlock = (block) * blockSize + 1;
        
        // 만약, 현재 페이지가 마지막 페이지라면?
        if(startPageInThisBlock == boards.getTotalPages()){
            lastPageInThisBlock = startPageInThisBlock;
        } else{
            lastPageInThisBlock = (startPageInThisBlock + blockSize - 1);
        }
        
        // 이전 블럭의 시작 페이지 ? 1 * 5 - 5 = 0
        pageInPrevBlock = (block * blockSize) - blockSize;
        // 다음 블럭의 시작 페이지 ? 1 + 1 * 5 = 10
        pageInNextBlock = (block + 1) * blockSize;
    }
}

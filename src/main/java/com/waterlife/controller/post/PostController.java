package com.waterlife.controller.post;

import com.waterlife.consts.SessionConst;
import com.waterlife.controller.MemberInformationUtil;
import com.waterlife.service.board.BoardInformationResponse;
import com.waterlife.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final BoardService boardService;
    private final MemberInformationUtil memberInformationUtil;

    @GetMapping("/new")
    public String writePage(){
        return "post/post";
    }

    @PostMapping
    public String writePost(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
            @ModelAttribute WritePostRequest request){
        log.info("request = {}", request);
        Long boardId = boardService.writePost(memberId, request);

        return "redirect:/posts/"+boardId;
    }

    @GetMapping("/{boardId}")
    public String detailPost(@PathVariable Long boardId,
                             @SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId, Model model){
        memberInformationUtil.getMemberInformation(memberId, model);

        BoardInformationResponse board = boardService.findBoardById(boardId);
        model.addAttribute("board", board);

        return "post/detail";
    }
}

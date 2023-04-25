package com.waterlife.controller.post;

import com.waterlife.consts.SessionConst;
import com.waterlife.controller.MemberInformationUtil;
import com.waterlife.service.board.BoardInformationResponse;
import com.waterlife.service.board.BoardService;
import com.waterlife.service.board.WritePostRequest;
import com.waterlife.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final MemberInformationUtil memberInformationUtil;

    @GetMapping("/new")
    public String writePage(){
        return "post/post";
    }

    @PostMapping
    public String writePost(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
            @ModelAttribute WritePostRequest request){
        Long boardId = boardService.writePost(memberId, request);

        return "redirect:/posts/"+boardId;
    }

    @GetMapping("/{boardId}")
    public String detailPost(@PathVariable Long boardId,
                             @SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId, Model model){
        memberInformationUtil.getMemberInformation(memberId, model);
        model.addAttribute("memberId", memberId);

        BoardInformationResponse board = boardService.findBoardById(boardId);
        model.addAttribute("board", board);

        List<CommentDto> comments = commentService.findByBoardId(boardId);
        model.addAttribute("comments", comments);

        boardService.addBoardViews(boardId);
        return "post/detail";
    }
}

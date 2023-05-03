package com.waterlife.controller.post;

import com.waterlife.consts.SessionConst;
import com.waterlife.controller.ResultResponse;
import com.waterlife.repository.SearchBoardDto;
import com.waterlife.service.board.BoardInformationResponse;
import com.waterlife.service.board.BoardModifyResponse;
import com.waterlife.service.board.BoardService;
import com.waterlife.service.board.WritePostRequest;
import com.waterlife.service.comment.CommentDto;
import com.waterlife.service.comment.CommentService;
import com.waterlife.service.comment.NestedCommentDto;
import com.waterlife.service.comment.NestedCommentService;
import com.waterlife.service.utils.MemberInformationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final NestedCommentService nestedCommentService;
    private final MemberInformationUtil memberInformationUtil;

    @GetMapping("/new")
    public String writePage(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                            Model model){
        memberInformationUtil.getMemberInformation(memberId, model);
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

        BoardInformationResponse board = boardService.findByBoardId(boardId);
        model.addAttribute("board", board);

        List<CommentDto> comments = commentService.findByBoardId(boardId);
        model.addAttribute("comments", comments);

        List<NestedCommentDto> nestedComments = nestedCommentService.findByBoardId(boardId);
        model.addAttribute("nestedComments", nestedComments);

        for (NestedCommentDto nestedComment : nestedComments) {
            log.info("nestedComment={}", nestedComment);
        }

        boardService.addBoardViews(boardId);
        return "post/detail";
    }

    @GetMapping("/modify/{boardId}")
    public String updateForm(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                             @PathVariable("boardId") Long boardId, Model model){
        BoardModifyResponse response = boardService.modifyForm(memberId, boardId);
        model.addAttribute("board", response);

        memberInformationUtil.getMemberInformation(memberId, model);

        return "post/modify";
    }

    @PostMapping("/modify/{boardId}")
    public String updateBoard(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                                                      @ModelAttribute WritePostRequest request,
                                                      @PathVariable("boardId") Long boardId){
        boardService.updatePost(memberId, boardId, request);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), "게시글 수정 완료", true
        );
        return "redirect:/posts/"+boardId;
    }

    @DeleteMapping("/{boardId}")
    @ResponseBody
    public ResponseEntity<ResultResponse> deleteBoard(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                                      @PathVariable("boardId") Long boardId){
        boardService.deleteOwnPost(memberId, boardId);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), "게시글 삭제 완료", true
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }

    @GetMapping("")
    public String search(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                         @ModelAttribute SearchCond searchCond,
                         @PageableDefault(size = 15) Pageable pageable, Model model){
        memberInformationUtil.getMemberInformation(memberId, model);

        Page<SearchBoardDto> boards = boardService.getBoardSearchResult(searchCond, pageable);
        model.addAttribute("boards", boards);
        PagingResponse response = new PagingResponse(boards);
        model.addAttribute("response", response);

        return "post/search";
    }

    @DeleteMapping("/admin/{boardId}")
    public ResponseEntity<ResultResponse> adminDeleteBoard(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId,
                                                      @PathVariable("boardId") Long boardId){
        boardService.deletePostWithAdminPermission(memberId, boardId);

        ResultResponse resultResponse = new ResultResponse(
                HttpStatus.OK.toString(), "게시글 삭제 완료", true
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }

}

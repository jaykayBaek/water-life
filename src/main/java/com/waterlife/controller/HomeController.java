package com.waterlife.controller;

import com.waterlife.consts.SessionConst;
import com.waterlife.service.board.BoardService;
import com.waterlife.service.utils.MemberInformationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberInformationUtil memberInformationUtil;
    private final BoardService boardService;

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId, Model model){
        memberInformationUtil.getMemberInformation(memberId, model);

        List<HomeViewBoardDto> recommendablePosts = boardService.findFiveRecommendablePosts();
        List<HomeViewBoardDto> newPosts = boardService.findFiveNewPosts();
        List<HomeViewBoardDto> questionPosts = boardService.findFiveQuestionPosts();
        List<HomeViewBoardDto> generalPosts = boardService.findFiveGeneralPosts();

        model.addAttribute("recommendablePosts", recommendablePosts);
        model.addAttribute("newPosts", newPosts);
        model.addAttribute("questionPosts", questionPosts);
        model.addAttribute("generalPosts", generalPosts);
        return "home/home";
    }
}

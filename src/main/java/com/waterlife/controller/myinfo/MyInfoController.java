package com.waterlife.controller.myinfo;

import com.waterlife.consts.SessionConst;
import com.waterlife.controller.MemberInformationUtil;
import com.waterlife.service.board.BoardService;
import com.waterlife.service.board.MyWrotePostsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/my-info")
public class MyInfoController {
    private final BoardService boardService;
    private final MemberInformationUtil memberInformationUtil;

    @GetMapping
    public String myInfo(){
        return "my-info/my-info";
    }
    @GetMapping("/posts")
    public String myWritePosts(@SessionAttribute(name = SessionConst.MEMBER_ID, required = false) Long memberId, Model model,
                               @PageableDefault(size = 15, sort = "id") Pageable pageable){
        memberInformationUtil.getMemberInformation(memberId, model);
        Page<MyWrotePostsDto> posts = boardService.findMyWrotePosts(memberId, pageable);
        model.addAttribute("posts", posts);
        return "my-info/posts";
    }

    @GetMapping("/comments")
    public String myWriteComments(){
        return "";
    }
}

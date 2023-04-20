package com.waterlife.controller.myinfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyInfoController {

    @GetMapping("/my-info")
    public String myInfo(){
        return "my-info/my-info";
    }
}

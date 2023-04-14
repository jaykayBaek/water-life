package com.waterlife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
public class HomeController {

    @RequestMapping
    public String home(){
        return "home/home";
    }
}

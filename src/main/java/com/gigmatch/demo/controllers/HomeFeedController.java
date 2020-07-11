package com.gigmatch.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeFeedController {

    @GetMapping("/feed")
    public String feed(){
        return "homeFeed";
    }
}

package com.gigmatch.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcomePage";
    }
}

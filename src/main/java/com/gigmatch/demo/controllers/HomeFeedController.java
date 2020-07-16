//package com.gigmatch.demo.controllers;
//
//import com.gigmatch.demo.daos.PostsRepository;
//import com.gigmatch.demo.daos.UsersRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//
//@Controller
//public class HomeFeedController {
//
//    private PostsRepository postsDao;
//    private UsersRepository usersDao;
//
//    public HomeFeedController(PostsRepository postsRepository, UsersRepository usersRepository) {
//        this.postsDao = postsRepository;
//        this.usersDao = usersRepository;
//    }
//
//    @GetMapping("/feed")
//    public String feed() {
//        return "postsFeed";
//    }
//
//}
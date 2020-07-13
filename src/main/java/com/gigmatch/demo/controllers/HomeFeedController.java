package com.gigmatch.demo.controllers;

import com.gigmatch.demo.models.Post;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeFeedController {

    @GetMapping("/feed")
    public String feed(){
        return "homeFeed";
    }

    @GetMapping("/posts/create")
    public String viewCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }
    @PostMapping("/posts/create")
    public String savePost(@ModelAttribute Post newPost) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setOwner(currentUser);
        Post savedPost = postsDao.save(newPost);
        return "redirect:/posts/" + savedPost.getId();
}

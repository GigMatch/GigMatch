package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.PostsRepository;
import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.Post;
import org.apache.catalina.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class HomeFeedController {

    private PostsRepository postsDao;
    private UsersRepository usersDao;

    public HomeFeedController(PostsRepository postsRepository, UsersRepository usersRepository) {
        this.postsDao = postsRepository;
        this.usersDao = usersRepository;
    }

    @GetMapping("/feed")
    public String feed(Model model) {
        model.addAttribute("post", new Post());
        return "/homeFeed";
    }

    @GetMapping("/posts/create")
    public String viewCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }
    @PostMapping("/posts/create")
    public String savePost(@ModelAttribute Post newPost) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setOwner((com.gigmatch.demo.models.User) currentUser);
        Post savedPost = postsDao.save(newPost);
        return "redirect:/feed" + savedPost.getId();
}
}
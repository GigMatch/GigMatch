package com.gigmatch.demo.controllers;


import com.gigmatch.demo.daos.PostCommentsRepository;
import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.PostComment;
import com.gigmatch.demo.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommentController {

    private PostCommentsRepository postCommentsDao;
    private UsersRepository usersDao;

    public CommentController (PostCommentsRepository postCommentsRepository, UsersRepository usersRepository){
        this.postCommentsDao = postCommentsRepository;
        this.usersDao = usersRepository;
    }

    @GetMapping("/feed/posts")
    public String indexComment(Model model) {
        List<PostComment> postCommentList = postCommentsDao.findAll();
        model.addAttribute("noCommentsFound", postCommentList.size() == 0);
        model.addAttribute("comments", postCommentList);
        return "posts/postsFeed";

    }


    @GetMapping("/feed/posts")
    public String showCommentForm(Model viewModel){
        viewModel.addAttribute("comment", new PostComment());
        return "posts/postsFeed";
    }

    @PostMapping("/feed/posts")
    public String updateComment(@ModelAttribute PostComment postCommentToBeSaved) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        postCommentToBeSaved.setOwner(currentUser);
        PostComment savedPostComment = postCommentsDao.save(postCommentToBeSaved);
//        return "redirect:/feed" + savedPost.getId();
        return "redirect:/feed/posts";
    }


}

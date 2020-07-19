package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.CommentsRepository;
import com.gigmatch.demo.daos.PostsRepository;
import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.PostComment;
import com.gigmatch.demo.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private PostsRepository postsDao;
    private CommentsRepository commentsDao;

    public CommentController(PostsRepository postsDao, CommentsRepository commentsDao){
        this.postsDao = postsDao;
        this.commentsDao = commentsDao;
    }

    @GetMapping("/posts/{id}/comment")
    public String showCommentForm(Model model, @PathVariable long id){
        Post postToComment = postsDao.getOne(id);
        PostComment newComment = new PostComment();
//        model.addAttribute("post", postToComment);
        model.addAttribute("comment", newComment);
        model.addAttribute("post", postToComment);
        return "posts/addComment";
    }

    @PostMapping("/posts/{id}/comment")
    public String addComment(@ModelAttribute PostComment comment, @ModelAttribute Post post){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setOwner(currentUser);
        comment.setPost(post);
        commentsDao.save(comment);
        return "redirect:/feed/posts";
    }
}

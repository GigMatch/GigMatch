
package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.CommentsRepository;
import com.gigmatch.demo.daos.PostsRepository;
import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.PostComment;
import com.gigmatch.demo.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        model.addAttribute("post", postToComment);
        return "posts/addComment";
    }

    @PostMapping("/posts/{id}/comment")
    public String addComment(@RequestParam(name = "comment") String comment, @PathVariable long id){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Post post = postsDao.getOne(id);

        PostComment postComment = new PostComment(post, currentUser, comment);

        commentsDao.save(postComment);

        return "redirect:/feed/posts";
    }

}



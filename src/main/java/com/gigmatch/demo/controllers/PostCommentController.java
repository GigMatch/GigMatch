package com.gigmatch.demo.controllers;


import com.gigmatch.demo.daos.PostCommentsRepository;
import com.gigmatch.demo.daos.PostsRepository;
import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.PostComment;
import com.gigmatch.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.util.Date;
import java.util.List;

@Controller
public class PostCommentController {

    private PostCommentsRepository postCommentsDao;
    private UsersRepository usersDao;
    private PostsRepository postsDao;

    public PostCommentController(PostCommentsRepository postCommentsRepository, UsersRepository usersRepository, PostsRepository postsRepository){
        this.postCommentsDao = postCommentsRepository;
        this.usersDao = usersRepository;
        this.postsDao = postsRepository;
    }
//
//    @GetMapping("/feed/posts")
//    public String indexComment(Model model) {
//        List<PostComment> postCommentList = postCommentsDao.findAll();
//        model.addAttribute("noCommentsFound", postCommentList.size() == 0);
//        model.addAttribute("comments", postCommentList);
//        return "posts/postsFeed";
//
//    }
//

    @GetMapping("/feed/posts")
    public String showCommentForm(Model viewModel){
        viewModel.addAttribute("comment", new PostComment());
        return "posts/postsFeed";
    }

    @PostMapping("/feed/posts")
    public String createComment(@ModelAttribute PostComment postCommentToBeSaved)  {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        postCommentToBeSaved.setPost(post);
        postCommentToBeSaved.setOwner(currentUser);
        Date currentDate = new Date();
        postCommentToBeSaved.setCreateDate(currentDate);
        PostComment savedComment = postCommentsDao.save(postCommentToBeSaved);
        return "redirect:/feed/posts" + savedComment.getPost().getId();
    }


}

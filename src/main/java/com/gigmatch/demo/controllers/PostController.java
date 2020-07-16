package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.PostsRepository;
import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    private PostsRepository postDao;
    private UsersRepository usersDao;

    public PostController(PostsRepository postsRepository, UsersRepository usersRepository){
        this.postDao = postsRepository;
        this.usersDao = usersRepository;

    }

    @GetMapping("/feed/posts")
//    @RequestMapping(value = "/ads", method = RequestMethod.GET)
    public String index(Model model) {
        List<Post> postList = postDao.findAll();
        model.addAttribute("noPostsFound", postList.size() == 0);
        model.addAttribute("posts", postList);
        return "/posts/postsFeed";

    }

//    @GetMapping("/posts/{id}")
//    public String show(@PathVariable long id, Model model){
//        Post ad = postDao.getOne(id);
//        model.addAttribute("postId", id);
//        model.addAttribute("post", ad);
//        return "/static/homeFeed";
//    }

    @GetMapping("/posts/create")
    public String showForm(Model viewModel){
        viewModel.addAttribute("post", new Post());
        return "/posts/CreateAPost";
    }

    @PostMapping("/posts/create")
    public String save(@ModelAttribute Post postToBeSaved, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postToBeSaved.setOwner(currentUser);
        Post savedPost = postDao.save(postToBeSaved);

//        return "redirect:/feed" + savedPost.getId();
        return "redirect:/feed/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditForm(Model model, @PathVariable long id){
        // find an ad
        Post postToEdit = postDao.getOne(id);
        model.addAttribute("post", postToEdit);
        return "posts/editAPost";
    }

    @PostMapping("/posts/{id}/edit")
    public String update(@ModelAttribute Post postToEdit){
        User currentUser = usersDao.getOne(1L);
        postToEdit.setOwner(currentUser);
        // save the changes
        postDao.save(postToEdit); // update ads set title = ? where id = ?
        return "redirect:/feed/posts";
    }

}

package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.CommentsRepository;
import com.gigmatch.demo.daos.PostsRepository;
import com.gigmatch.demo.daos.ProfilesRepository;
import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.PostComment;
import com.gigmatch.demo.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private PostsRepository postsDao;
    private UsersRepository usersDao;
    private ProfilesRepository profilesDao;
    private CommentsRepository commentsDao;

    public PostController(PostsRepository postsDao, UsersRepository usersDao, ProfilesRepository profilesDao, CommentsRepository commentsDao){
        this.postsDao = postsDao;
        this.usersDao = usersDao;
        this.profilesDao = profilesDao;
        this.commentsDao = commentsDao;
    }

    @GetMapping("/feed/posts")
//    @RequestMapping(value = "/ads", method = RequestMethod.GET)
    public String index(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> postList = postsDao.findAll();
        model.addAttribute("noPostsFound", postList.size() == 0);
        model.addAttribute("posts", postList);
        if(profilesDao.findByOwner(currentUser) == null) {
            model.addAttribute("hasProfile", false);
        } else {
            model.addAttribute("hasProfile", true);
            model.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());
        }
        return "posts/postsFeed";
    }

//    @GetMapping("/sign-up")
//    public String showSignupForm(Model model){
//        model.addAttribute("user", new User());
//        return "users/sign-up";
//    }

    @GetMapping("/posts/create")
    public String showForm(Model viewModel){
        viewModel.addAttribute("post", new Post());
        return "posts/CreateAPost";
    }

    @PostMapping("/posts/create")
    public String save(@ModelAttribute Post postToBeSaved, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postToBeSaved.setOwner(currentUser);
        Post savedPost = postsDao.save(postToBeSaved);

//        return "redirect:/feed" + savedPost.getId();
        return "redirect:/feed/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditForm(Model model, @PathVariable long id){
        // find an ad
        Post postToEdit = postsDao.getOne(id);
        model.addAttribute("post", postToEdit);
        return "posts/editAPost";
    }

    @PostMapping("/posts/{id}/edit")
    public String update(@ModelAttribute Post postToEdit){
        User currentUser = usersDao.getOne(1L);
        postToEdit.setOwner(currentUser);
        // save the changes
        postsDao.save(postToEdit); // update ads set title = ? where id = ?
        return "redirect:/feed/posts";
    }


    @PostMapping("/posts/{id}/delete")
    public String destroy(@PathVariable long id){
        postsDao.deleteById(id);
        return "redirect:/feed/posts";
    }

    @GetMapping("/search/posts")
    public String searchByBody(Model model, @RequestParam(name = "term") String term){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());
        List<Post> postList = postsDao.searchByBody(term);
        model.addAttribute("posts", postList);
        return "posts/postsFeed";
    }
}
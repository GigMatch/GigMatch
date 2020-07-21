package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.CommentsRepository;
import com.gigmatch.demo.daos.PostsRepository;
import com.gigmatch.demo.daos.ProfilesRepository;
import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.PostComment;
import com.gigmatch.demo.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    @Value("${filestack.api.key}")
    private String apiKey;

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

    @GetMapping("posts/{id}")
    public String showOne(@PathVariable long id, Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postsDao.getOne(id);
        List<PostComment> comments = commentsDao.findAllByPost(post);

        User postOwner = post.getOwner();
        model.addAttribute("postOwner", postOwner);
        model.addAttribute("postOwnerProfileId", profilesDao.findByOwner(postOwner).getId());
        model.addAttribute("post", post);
        model.addAttribute("profile", profilesDao.findByOwner(currentUser));
        model.addAttribute("myProfileId", profilesDao.findByOwner(currentUser).getId());
        model.addAttribute("comments", comments);
        return "posts/showPost";
    }

    @GetMapping("/posts/create")
    public String showForm(Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("post", new Post());
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("myProfileId", profilesDao.findByOwner(currentUser).getId());

        return "posts/CreateAPost";
    }

    @PostMapping("/posts/create")
    public String save(@ModelAttribute Post postToBeSaved, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postToBeSaved.setOwner(currentUser);
        Post savedPost = postsDao.save(postToBeSaved);
        model.addAttribute("myProfileId", profilesDao.findByOwner(currentUser).getId());

//        return "redirect:/feed" + savedPost.getId();
        return "redirect:/feed/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditForm(Model model, @PathVariable long id){
        // find an ad
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post postToEdit = postsDao.getOne(id);
        model.addAttribute("post", postToEdit);
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());

        return "posts/editAPost";
    }

    @PostMapping("/posts/{id}/edit")
    public String update(@ModelAttribute Post postToEdit, Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());
        postToEdit.setOwner(currentUser);
        // model.addAttribute("apiKey", apiKey);
        // save the changes
        postsDao.save(postToEdit); // update ads set title = ? where id = ?
        return "redirect:/feed/posts";
    }


    @PostMapping("/posts/{id}/delete")
    public String destroy(@PathVariable long id){
        Post postToDelete = postsDao.getOne(id);
        List<PostComment> comments = commentsDao.findAllByPost(postToDelete);
        commentsDao.deleteAll(comments);
        postsDao.deleteById(id);

        return "redirect:/feed/posts";
    }

    @GetMapping("/search/posts")
    public String searchByBody(Model model, @RequestParam(name = "term") String term){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());
        List<Post> resultList = postsDao.searchByBody(term);
        model.addAttribute("results", resultList);
        model.addAttribute("noResultsFound", resultList.size() == 0);

        return "posts/searchResults";
    }

    //Post Reactions
    @PostMapping("/posts/{id}/reactions")
    public String postReactions(Model model, @PathVariable long id){
        //Getting current USER OBJECT, all properties
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Getting the "RIGHT" post id
        Post post = postsDao.getOne(id);
        //Get the current reactions list from that post
        List<User> reactionsList = post.getUserReactions();
        //Add user's reaction to the reaction List
        reactionsList.add(currentUser);
        //Overriding the reactions List by adding the current user's reaction to the list
        post.setUserReactions(reactionsList);
        //Save the Post back to database
        postsDao.save(post);
        //returning the view to this feed
        return "redirect:/feed/posts";
    }
}
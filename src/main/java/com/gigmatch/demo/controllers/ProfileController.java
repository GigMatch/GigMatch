package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.PostsRepository;
import com.gigmatch.demo.daos.ProfilesRepository;
import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.Profile;
import com.gigmatch.demo.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProfileController {

    @Value("${filestack.api.key}")
    private String apiKey;

    private UsersRepository usersDao;
    private PasswordEncoder passwordEncoder;
    private ProfilesRepository profilesDao;
    private PostsRepository postsDao;

    public ProfileController(UsersRepository usersDao, PasswordEncoder passwordEncoder, ProfilesRepository profilesDao, PostsRepository postsDao) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
        this.profilesDao = profilesDao;
        this.postsDao = postsDao;
    }

    // Reading current user profile
    @GetMapping("/my-profile/{id}")
    public String showMyProfile(@PathVariable long id, Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile profile = profilesDao.getOne(id);
        List<Post> postList = postsDao.findAllByOwner(currentUser);
        model.addAttribute("noPostsFound", postList.size() == 0);
        model.addAttribute("profile", profile);
        model.addAttribute("owner", profile.getOwner());
        model.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());
        model.addAttribute("userPosts", postList);
        return "users/myProfile";
    }


    @GetMapping("/profile/{id}")
    public String showOtherProfile(@PathVariable long id, Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile profile = profilesDao.getOne(id);
        User profileOwner = profile.getOwner();
        List<Post> otherUserPostList = postsDao.findAllByOwner(profileOwner);
        model.addAttribute("noPostsFound", otherUserPostList.size() == 0);
        model.addAttribute("profile", profile);
        model.addAttribute("owner", profile.getOwner());
        model.addAttribute("currentUserProfileId", profilesDao.findByOwner(currentUser).getId());
        model.addAttribute("profileId", id);
        model.addAttribute("userPosts", otherUserPostList);
        return "users/otherUsersProfile";
    }


    // Getting create form for profile
    @GetMapping("/profile/create")
    public String showCreateProfileForm(Model model) {
        model.addAttribute("profile", new Profile());
        return "posts/postsFeed";
    }

    // Creating a profile for user
    @PostMapping("/profile/create")
    public String saveProfile(@ModelAttribute Profile userProfile) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userProfile.setOwner(currentUser);
        Profile savedProfile = profilesDao.save(userProfile);

        return "redirect:/my-profile/" + savedProfile.getId();
    }

    // Read current user profile update form
    @GetMapping("/profile/{id}/edit")
    public String showUpdateProfileForm(Model model, @PathVariable long id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile profileToEdit = profilesDao.getOne(id);
        model.addAttribute("profile", profileToEdit);
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("myProfileId", profilesDao.findByOwner(currentUser).getId());
        return "profiles/edit";
    }

    // Update current user profile
    @PostMapping("/profile/{id}/edit")
    public String update(@ModelAttribute Profile profileToEdit, Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileToEdit.setOwner(currentUser);
        profilesDao.save(profileToEdit);
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("profileId", profileToEdit.getId());
        model.addAttribute("myProfileId", profilesDao.findByOwner(currentUser).getId());
        return "redirect:/my-profile/" + profileToEdit.getId();
    }
}

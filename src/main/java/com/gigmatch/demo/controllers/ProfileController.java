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

    // Creating a profile for user and adding Placeholder Profile Image
    @PostMapping("/profile/create")
    public String saveProfile(@ModelAttribute Profile userProfile) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userProfile.setOwner(currentUser);

        //User Profile Photo Placeholder Image
        if(userProfile.getProfilePhoto() == null){
            userProfile.setProfilePhoto("data:image/png;base64,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");
        }
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

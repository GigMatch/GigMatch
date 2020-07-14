package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.ProfilesRepository;
import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.Profile;
import com.gigmatch.demo.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    private UsersRepository users;
    private PasswordEncoder passwordEncoder;
    private ProfilesRepository profiles;

    public ProfileController(UsersRepository users, PasswordEncoder passwordEncoder, ProfilesRepository profiles) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.profiles = profiles;
    }

//    @GetMapping("/profile")
//    public String showProfileForm(Model model){
//        model.addAttribute("user", new User());
//        return "/users/updateProfile";
//    }
//
//    @PostMapping("/profile")
//    public String saveProfileForm(@ModelAttribute User userProfileToBeSaved){
//        String hash = passwordEncoder.encode(userProfileToBeSaved.getPassword());
//        userProfileToBeSaved.setPassword(hash);
//        users.save(userProfileToBeSaved);
//        return "buildprofile";
//    }

    @GetMapping("/profile/{id}")
    public String showUserProfile(@PathVariable long id, Model model) {
        model.addAttribute("profile", new Profile());
        return "users/loggedInProfile";
    }

    //logged in user's profile
    @GetMapping("/profile/{id}")
    public String saveUserProfile(@PathVariable long id, Model model) {
        model.addAttribute("profile", profiles.findProfileByUserId(id));
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userName", currentUser.getUsername());
        return "users/loggedInProfile";
    }
}

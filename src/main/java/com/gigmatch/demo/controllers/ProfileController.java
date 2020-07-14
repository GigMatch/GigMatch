package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.Profile;
import com.gigmatch.demo.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    private UsersRepository users;
    private PasswordEncoder passwordEncoder;

    public ProfileController(UsersRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public String showProfileForm(){
        return "/users/otherUsersProfile";
    }
//
//    @PostMapping("/profile")
//    public String saveProfileForm(@ModelAttribute User userProfileToBeSaved){
//        String hash = passwordEncoder.encode(userProfileToBeSaved.getPassword());
//        userProfileToBeSaved.setPassword(hash);
//        users.save(userProfileToBeSaved);
//        return "buildprofile";
//    }

    @GetMapping("/my-profile")
    public String showMyProfile(){
        return "/users/myProfile";
    }

}

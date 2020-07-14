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
    private UsersRepository usersDao;
    private PasswordEncoder passwordEncoder;
    private ProfilesRepository profilesDao;

    public ProfileController(UsersRepository usersDao, PasswordEncoder passwordEncoder, ProfilesRepository profilesDao) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
        this.profilesDao = profilesDao;
    }


    @GetMapping("/my-profile/{id}")
    public String showMyProfile(@PathVariable long id, Model model){
//        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("profile", new Profile());
        return "/users/myProfile";
    }

    @GetMapping("/profile/{id}")
    public String showProfileForm(){
        return "/users/otherUsersProfile";
    }

//    @PostMapping("/profile")
//    public String saveProfileForm(@ModelAttribute User userProfileToBeSaved){
//        String hash = passwordEncoder.encode(userProfileToBeSaved.getPassword());
//        userProfileToBeSaved.setPassword(hash);
//        users.save(userProfileToBeSaved);
//        return "buildprofile";
//    }

    @PostMapping("/profile/create")
    public String saveProfile(@ModelAttribute Profile newProfile) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newProfile.setOwner(currentUser);
        Profile savedProfile = profilesDao.save(newProfile);
        return "redirect:/my-profile/{id}" + savedProfile.getId();
    }



}

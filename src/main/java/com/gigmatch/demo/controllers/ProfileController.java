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

    // Reading current user profile
    @GetMapping("/my-profile/{id}")
    public String showMyProfile(@PathVariable long id, Model model){
//        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("profile", new Profile());
        model.addAttribute("profileId", id);
        return "/users/myProfile";
    }

    // Reading other user profile
//    @GetMapping("/profile/{id}")
//    public String showOtherUser(){
//        return "/users/otherUsersProfile";
//    }

    // Creating a profile for user
    @PostMapping("/profile/create")
    public String saveProfile(@ModelAttribute Profile userProfile, @PathVariable long id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userProfile.setOwner(currentUser);
        Profile savedProfile = profilesDao.save(userProfile);
        return "redirect:/my-profile/" + savedProfile.getId();
    }

    // Read current user profile update form
    @GetMapping("/profile/{id}/edit")
    public String showUpdateProfileForm(Model model, @PathVariable long id) {
        Profile profileToEdit = profilesDao.getOne(id);
        model.addAttribute("profile", profileToEdit);
        return "profiles/edit";
    }

    // Update current user profile
    @PostMapping("/profile/{id}/edit")
    public String update(@ModelAttribute Profile profileToEdit){
        User currentUser = usersDao.getOne(1L);
        profileToEdit.setOwner(currentUser);
        profilesDao.save(profileToEdit);
        return "redirect:/profile/" + profileToEdit.getId();
    }











}

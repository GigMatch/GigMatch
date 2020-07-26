package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UsersRepository usersDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UsersRepository usersDao, PasswordEncoder passwordEncoder) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User newUser, @Validated User user, Errors validation, Model model){
        String username = user.getUsername();
        String email = user.getEmail();
        User userExists = usersDao.findByUsername(username);
        User emailExists = usersDao.findByEmail(email);

        if (userExists != null) {
            validation.rejectValue("username", "user.username", username + " already exists in our records.");
        }

        if (emailExists != null) {
            validation.rejectValue("email", "user.email", email + " already exists in our records.");
        }

        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("user", user);
            return "users/sign-up";
        }

        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        usersDao.save(newUser);
        return "redirect:/login";
    }
}
package com.fitclinic.fitclinic.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fitclinic.fitclinic.model.User;
import com.fitclinic.fitclinic.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Register form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.register(user);
        return "redirect:/users";
    }

    // List all users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users-list";
    }

    // View one profile
    @GetMapping("/{id}")
    public String viewProfile(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "profile";
    }

    // Edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user-edit";
    }

    // Update profile
    @PostMapping("/{id}/edit")
    public String updateProfile(@PathVariable String id, @ModelAttribute User updated) {
        userService.update(id, updated);
        return "redirect:/users/" + id;
    }

    // Delete user
    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable String id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
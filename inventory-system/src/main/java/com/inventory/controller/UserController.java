package com.inventory.controller;

import com.inventory.model.User;
import com.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @GetMapping
    public String list(@RequestParam(required = false) String id,
                       @RequestParam(required = false) String username,
                       Model model) {
        var all = userService.findAll();
        if (id != null && !id.isBlank()) {
            all = all.stream().filter(u -> u.getId().equalsIgnoreCase(id)).toList();
        } else if (username != null && !username.isBlank()) {
            all = all.stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).toList();
        }
        model.addAttribute("users", all);
        model.addAttribute("searchId", id);
        model.addAttribute("searchUsername", username);
        return "users/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    @PostMapping
    public String save(@ModelAttribute User user) {
        if (user.getPasswordHash() != null) {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "users/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user) {
        if (user.getPasswordHash() != null) {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
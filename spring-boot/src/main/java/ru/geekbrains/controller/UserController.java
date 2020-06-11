package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.persist.entity.User;
import ru.geekbrains.service.RoleService;
import ru.geekbrains.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String userList(Model model,
                           @RequestParam(value = "minAge", required = false) Integer minAge,
                           @RequestParam(value = "maxAge", required = false) Integer maxAge) {
        logger.info("User list. With minAge = {} and maxAge = {}", minAge, maxAge);

        model.addAttribute("users", userService.filterByAge(minAge, maxAge));
        return "users";
    }

    @GetMapping("new")
    public String createUser(Model model) {
        logger.info("Create user form");

        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "user";
    }

    @PostMapping
    public String saveUser(User user) {
        logger.info("Save user method");

        userService.save(user);
        return "redirect:/user";
    }
}

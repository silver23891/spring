package ru.geekbrains.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/site")
@Controller
public class SiteController {
    @GetMapping("login")
    public String login() {
        return "login";
    }
}

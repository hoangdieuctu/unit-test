package com.hoangdieuctu.boot.sample.controller;

import com.hoangdieuctu.boot.sample.model.User;
import com.hoangdieuctu.boot.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseBody
    public User getUser(@RequestParam("name") String name) {
        return userService.getUser(name);
    }
}

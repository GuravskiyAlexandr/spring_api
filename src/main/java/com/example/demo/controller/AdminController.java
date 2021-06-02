package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Views;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @JsonView(Views.Name.class)
    @GetMapping(value = "/user")
    public User getUser(@AuthenticationPrincipal User user) {
        if (user != null) {
            user = userService.findUserById(user.getId());
            return user;
        }
        return null;
    }

    @JsonView(Views.Name.class)
    @GetMapping(value = "/admin/users")
    public List<User> allUsers() {
        return userService.getAllUsers();
    }

    @JsonView(Views.Name.class)
    @PostMapping("/admin/userAdd")
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @JsonView(Views.Name.class)
    @PostMapping(value = "/admin/edit")
    public User editUser(@RequestBody User newUser) {
        System.out.println(newUser);
        return userService.userEdit(newUser);
    }

    @JsonView(Views.Name.class)
    @GetMapping(value = "/admin/user/{id}")
    @ResponseBody
    public User findUserForEdit(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @DeleteMapping(value = "/admin/delete")
    public boolean delete(@RequestBody User user) {
          userService.userDelete(user);
          return true;
    }
}

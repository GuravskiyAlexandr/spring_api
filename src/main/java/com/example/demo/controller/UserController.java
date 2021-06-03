package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Views;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @JsonView(Views.Name.class)
    @GetMapping
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
        if (user != null) {
            return ResponseEntity.ok()
                    .body(userService.findUserById(user.getId()));
        }
        return ResponseEntity.notFound().build();
    }
}

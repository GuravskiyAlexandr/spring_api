package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Views;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @JsonView(Views.Name.class)
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok()
                .body(userService.getAllUsers());
    }

    @JsonView(Views.Name.class)
    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.saveUser(user));
    }

    @JsonView(Views.Name.class)
    @PutMapping
    public ResponseEntity<User> editUser(@RequestBody User newUser) {
        return ResponseEntity.ok()
                .body(userService.saveUser(userService.userEdit(newUser)));
    }

    @JsonView(Views.Name.class)
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<User> findUserForEdit(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(userService.saveUser(userService.findUserById(id)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") User user) {
        userService.userDelete(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

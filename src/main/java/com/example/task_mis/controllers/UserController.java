package com.example.task_mis.controllers;

import com.example.task_mis.entities.Image;
import com.example.task_mis.entities.User;
import com.example.task_mis.services.ImageService;
import com.example.task_mis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;


    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/{id}/images")
    public List<Image> getUserImages(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return userService.getUserImages(id);
    }
}

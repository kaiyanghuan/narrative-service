package com.example.narrative.controllers;

import com.example.narrative.controllers.responses.UserResponse;
import com.example.narrative.entities.User;
import com.example.narrative.services.UserService;
import com.example.narrative.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseHelper responseHelper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ok(userService.getUsers().stream().map(user -> responseHelper.from(user).toUserResponse()).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        return ok(responseHelper.from(userService.getUser(id)).toUserResponse());
    }

    @GetMapping("/{name}/name")
    public ResponseEntity<UserResponse> getUserByName(@PathVariable String name) {
        return ok(responseHelper.from(userService.getUserByName(name)).toUserResponse());
    }

    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        return ok(responseHelper.from(userService.create(user)).toUserResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody User user, @PathVariable String id) {
        return ok(responseHelper.from(userService.update(user, id)).toUserResponse());
    }

    @PutMapping("/{name}/name")
    public ResponseEntity<UserResponse> updateUserByName(@RequestBody User user, @PathVariable String name) {
        return ok(responseHelper.from(userService.updateByName(user, name)).toUserResponse());
    }
}

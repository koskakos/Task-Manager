package com.task.manager.controller;

import com.task.manager.model.User;
import com.task.manager.model.UserInfo;
import com.task.manager.repository.UserRepository;
import com.task.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    // update user {firstName, lastName}
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserInfo userInfo) {
        var user = userService.updateUser(userInfo, userService.getAuthenticatedUser());
        return ResponseEntity.ok(user);
    }

    // getAuthenticatedUserId() {id: }
    @GetMapping("/authenticated")
    public ResponseEntity<?> getAuthenticatedUserId() {
        return ResponseEntity.ok(userService.getAuthenticatedUser().getId());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @RequestMapping(value = "/confirmemail", method = {RequestMethod.GET, RequestMethod.POST})
    private ResponseEntity<?> confirmEmail(@RequestParam("token") String token) {
        return userService.confirmEmail(token);
    }
}

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

    // update user {firstName, lastName}
    // getAuthenticatedUserId() {id: }
    //

    private final UserRepository userRepository;

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        var user = userRepository.findUserById(id);
        return ResponseEntity.ok(user.orElseThrow(()
                -> new NoSuchElementException(String.format("User with id '%d' not found", id))));
    }

    @RequestMapping(value = "/confirmemail", method = {RequestMethod.GET, RequestMethod.POST})
    private ResponseEntity<?> confirmEmail(@RequestParam("token") String token) {
        return userService.confirmEmail(token);
    }
}

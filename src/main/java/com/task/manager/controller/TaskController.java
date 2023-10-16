package com.task.manager.controller;

import com.task.manager.model.Task;
import com.task.manager.model.User;
import com.task.manager.repository.TaskRepository;
import com.task.manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @PutMapping("")
    public ResponseEntity<?> saveTask() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = Task.builder().user(user).build();
        taskRepository.save(task);
        return ResponseEntity.ok("saved");
    }

    @GetMapping("")
    public ResponseEntity<?> getTasks() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(user.getTasks());
    }
}
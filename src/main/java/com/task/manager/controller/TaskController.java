package com.task.manager.controller;

import com.task.manager.model.Task;
import com.task.manager.model.TaskInfo;
import com.task.manager.model.User;
import com.task.manager.model.request.TaskRequest;
import com.task.manager.repository.TaskRepository;
import com.task.manager.repository.UserRepository;
import com.task.manager.service.TaskService;
import com.task.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskService taskService;

    @PutMapping("")
    public ResponseEntity<?> saveTask(@RequestBody TaskRequest taskRequest) {
        Task task = taskService.saveTask(taskRequest, userService.getAuthenticatedUser());
        return ResponseEntity.ok(task);
    }

    @GetMapping("")
    public ResponseEntity<?> getTasks() {
        User user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(user.getTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Task with id '%d' not found", id))));
    }
}

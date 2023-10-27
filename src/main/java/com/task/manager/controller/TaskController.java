package com.task.manager.controller;

import com.task.manager.model.Task;
import com.task.manager.model.User;
import com.task.manager.model.request.TaskPointRequest;
import com.task.manager.model.request.TaskRequest;
import com.task.manager.service.TaskService;
import com.task.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final UserService userService;
    private final TaskService taskService;

    // delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(id, userService.getAuthenticatedUser()));
    }

    // delete point
    @DeleteMapping("/{taskId}/{pointId}")
    public ResponseEntity<?> deletePoint(@PathVariable Long taskId, @PathVariable Long pointId) {
        return ResponseEntity.ok(taskService.deletePoint(taskId, pointId, userService.getAuthenticatedUser()));
    }

    // add point
    @PostMapping("/{id}")
    public ResponseEntity<?> addPoint(@PathVariable Long id, @RequestBody TaskPointRequest taskPointRequest) {
        return ResponseEntity.ok(taskService.addPoint(id, taskPointRequest, userService.getAuthenticatedUser()));
    }
    // point completion json {"isComplete": false/true}

    @PutMapping("/{taskId}/{pointId}")
    public ResponseEntity<?> setPointCompletion(@RequestParam boolean completed,
                                               @PathVariable Long taskId, @PathVariable Long pointId) {
        return ResponseEntity.ok(taskService.setPointCompletion(taskId, pointId, completed, userService.getAuthenticatedUser()));
    }

    @PostMapping("")
    public ResponseEntity<?> saveTask(@RequestBody TaskRequest taskRequest) {
        Task task = taskService.saveTask(taskRequest, userService.getAuthenticatedUser());
        return ResponseEntity.ok(task);
    }


    @GetMapping("")
    public ResponseEntity<?> getTasks() {
        User user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(user.getTasks());
    }
    // For tests
    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findTaskById(id));
    }
}

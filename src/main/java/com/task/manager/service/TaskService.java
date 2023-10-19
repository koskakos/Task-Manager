package com.task.manager.service;


import com.task.manager.model.Task;
import com.task.manager.model.TaskInfo;
import com.task.manager.model.TaskPoint;
import com.task.manager.model.User;
import com.task.manager.model.request.TaskRequest;
import com.task.manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(TaskRequest taskRequest, User user) {
        Task task = Task.builder().user(user).taskInfo(TaskInfo.builder()
                        .taskTitle(taskRequest.getTaskTitle())
                        .taskDescription(taskRequest.getTaskDescription())
                        .start(taskRequest.getStartDate())
                        .end(taskRequest.getEndDate())
                        .points(taskRequest.getTaskPoints().stream().map(
                                (req) -> TaskPoint.builder().description(req.getPointDescription()).build()
                        ).toList())
                        .build())
                .build();
        taskRepository.save(task);
        return task;
    }
}

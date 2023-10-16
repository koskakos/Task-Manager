package com.task.manager.service;


import com.task.manager.model.Task;
import com.task.manager.model.TaskInfo;
import com.task.manager.model.User;
import com.task.manager.model.request.TaskRequest;
import com.task.manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

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
                        .startDate(taskRequest.getStartDate())
                        .endDate(taskRequest.getEndDate())
                        .build())
                .build();
        taskRepository.save(task);
        return task;
    }
}

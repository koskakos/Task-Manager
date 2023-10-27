package com.task.manager.service;


import com.task.manager.model.Task;
import com.task.manager.model.TaskInfo;
import com.task.manager.model.TaskPoint;
import com.task.manager.model.User;
import com.task.manager.model.request.TaskPointRequest;
import com.task.manager.model.request.TaskRequest;
import com.task.manager.repository.TaskPointRepository;
import com.task.manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskPointRepository taskPointRepository;

    public TaskService(TaskRepository taskRepository, TaskPointRepository taskPointRepository) {
        this.taskRepository = taskRepository;
        this.taskPointRepository = taskPointRepository;
    }

    public Task saveTask(TaskRequest taskRequest, User user) {
        Task task = Task.builder().user(user).taskInfo(TaskInfo.builder()
                        .title(taskRequest.getTitle())
                        .taskDescription(taskRequest.getTaskDescription())
                        .start(taskRequest.getStart())
                        .end(taskRequest.getEnd())
                        .status(taskRequest.getStatus())
                        .type(taskRequest.getType())
                        .points(taskRequest.getPoints().stream().map(
                                (req) -> TaskPoint.builder().pointDescription(req.getPointDescription()).build()
                        ).toList())
                        .build())
                    .build();
        taskRepository.save(task);
        return task;
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Task with id '%d' not found", id)));
    }

    public TaskPoint findTaskPointById(Long id) {
        return taskPointRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Task point with id '%d' not found", id)));
    }


    public Task deleteTask(Long id, User user) {
        Task task = findTaskById(id);
        if(haveAccessToTask(task, user)) {
            taskRepository.delete(task);
            return task;
        }
        return null;
    }

    public Task deletePoint(Long taskId, Long pointId, User user) {
        Task task = findTaskById(taskId);
        TaskPoint taskPoint = findTaskPointById(pointId);
        if(haveAccessToTask(task, user)) {
            if(task.getTaskInfo().getPoints().remove(taskPoint)) {
                taskRepository.save(task);
                taskPointRepository.deleteById(pointId);
                return findTaskById(taskId);
            }
            throw new NoSuchElementException(String.format("Task point with id '%d' in task with id '%d' not found", pointId, taskId));
        }
        return null;
    }

    public Task addPoint(Long id, TaskPointRequest taskPointRequest, User user) {
        Task task = findTaskById(id);
        if(haveAccessToTask(task, user)) {
            task.getTaskInfo().getPoints().add(TaskPoint.builder()
                    .pointDescription(taskPointRequest.getPointDescription())
                    .build());
            taskRepository.save(task);
            return task;
        }
        return null;
    }

    public Task setPointCompletion(Long taskId, Long pointId, boolean completed, User user) {
        Task task = findTaskById(taskId);
        TaskPoint taskPoint = findTaskPointById(pointId);
        if(haveAccessToTask(task, user)) {
            if(task.getTaskInfo().getPoints().contains(taskPoint)) {
                taskPoint.setCompleted(completed);
                taskPointRepository.save(taskPoint);
                return findTaskById(taskId);
            }
            throw new NoSuchElementException(String.format("Task point with id '%d' in task with id '%d' not found", pointId, taskId));
        }
        return null;
    }

    private boolean haveAccessToTask(Task task, User user) {
        return task.getUser().getId().equals(user.getId());
    }


}

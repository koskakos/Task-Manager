package com.task.manager.repository;

import com.task.manager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
//    Optional<Task> findTaskByTask_id(Long id);
}

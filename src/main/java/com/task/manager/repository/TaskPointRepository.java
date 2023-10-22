package com.task.manager.repository;

import com.task.manager.model.TaskPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskPointRepository extends JpaRepository<TaskPoint, Long> {

    void deleteById(Long id);
}

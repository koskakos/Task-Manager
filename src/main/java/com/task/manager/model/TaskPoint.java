package com.task.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Task_point")
public class TaskPoint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "point_id", nullable = false)
    private Long id;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "task_id")
//    private TaskInfo taskId;

    @Column(name = "point_description", nullable = false, length = 40)
    private String pointDescription;

    @Column(name = "point_is_completed")
    private boolean isCompleted;
}

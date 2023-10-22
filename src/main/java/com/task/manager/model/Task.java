package com.task.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Task")
public class Task {

    @Id
    @Column(name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnore
    private User user;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private TaskInfo taskInfo;
}

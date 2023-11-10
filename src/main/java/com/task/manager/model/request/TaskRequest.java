package com.task.manager.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private String title;
    private String taskDescription;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate start;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate end;
    private Byte status;
    private Byte type;

    private List<TaskPointRequest> points = new LinkedList<>();
}
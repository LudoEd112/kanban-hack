package com.kanban.hack.viewmodel;

import com.kanban.hack.model.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskVM {

    private Long id;
    private String title;
    private LocalDate date;
    private Status status;
    private Long projectId;
    private Long sprintId;
    private Long ownerId;

}

package com.kanban.hack.viewmodel;

import com.kanban.hack.model.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskVM {

    private Long id;
    private Long projectId;
    private Long creatorId;
    private String title;
    private String description;
    private Status status;


}

package com.kanban.hack.viewmodel;

import com.kanban.hack.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskVM {

    private Long id;
    private String title;
    private LocalDate date;
    private Status status;
    private Long boardId;
    private Long sprintId;
    private Long ownerId;

}

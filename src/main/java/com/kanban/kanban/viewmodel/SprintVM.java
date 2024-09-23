package com.kanban.kanban.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SprintVM {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long boardId;

}

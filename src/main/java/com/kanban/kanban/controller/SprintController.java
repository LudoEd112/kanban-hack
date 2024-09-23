package com.kanban.kanban.controller;

import com.kanban.kanban.model.Sprint;
import com.kanban.kanban.service.SprintService;
import com.kanban.kanban.viewmodel.SprintVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @PostMapping
    public void create(@RequestBody SprintVM sprintVM) {
        sprintService.create(sprintVM);
    }

    @GetMapping("/board/{boardId}")
    public List<SprintVM> listByBoard(@PathVariable Long boardId) {
        List<Sprint> sprints = sprintService.listByBoard(boardId);

        List<SprintVM> sprintVMS = sprints.stream().map(sprint -> {
            SprintVM sprintVM = new SprintVM();
            sprintVM.setId(sprint.getId());
            sprintVM.setStartDate(sprint.getStartDate());
            sprintVM.setEndDate(sprint.getEndDate());
            sprintVM.setBoardId(sprint.getBoard().getId());
            return sprintVM;
        }).collect(Collectors.toList());

        return sprintVMS;
    }
}

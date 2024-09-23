package com.kanban.hack.controller;

import com.kanban.hack.model.Sprint;
import com.kanban.hack.service.SprintService;
import com.kanban.hack.viewmodel.SprintVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Sprints", description = "Sprints API")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @PostMapping
    @Operation(summary = "Creating", description = "Crating new sprint")
    public void create(@RequestBody SprintVM sprintVM) {
        sprintService.create(sprintVM);
    }

    @GetMapping("/board/{boardId}")
    @Operation(summary = "Get sprint", description = "Get sprint by boardId")
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

package com.kanban.hack.controller;

import com.kanban.hack.model.Status;
import com.kanban.hack.model.Task;
import com.kanban.hack.model.User;
import com.kanban.hack.repository.TaskRepository;
import com.kanban.hack.service.TaskService;
import com.kanban.hack.viewmodel.TaskVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/tasks")
@Tag(name = "Tasks", description = "Task API")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @Operation(summary = "Create", description = "Create new task")
    public Task create(@RequestBody TaskVM taskVM) {
        return taskService.create(taskVM);
    }

    /*@GetMapping("/sprint/{sprintId}")
    @Operation(summary = "Find tasks", description = "Find tasks by sprintId")
    public List<TaskVM> listByTask(@PathVariable Long sprintId) {
        List<Task> tasks = taskService.listBySprint(sprintId);

        List<TaskVM> taskVMS = tasks.stream().map(task -> {
            TaskVM taskVM = new TaskVM();
            taskVM.setId(task.getId());
            taskVM.setTitle(task.getTitle());
            taskVM.setStatus(task.getStatus());
            taskVM.setProjectId(task.getProject().getId());
            return taskVM;
        }).collect(Collectors.toList());

        return taskVMS;
    }*/

    @GetMapping("/{taskId}")
    @Operation(summary = "Get tasks", description = "Get all tasks by Id")
    public List<Task> getAllTasks(@PathVariable Long taskId){
        return taskService.getAllTasks(taskId);
    }

    @PutMapping("/{taskId}/move/{newStatus}")
    @Operation(summary = "changeStatus", description = "change status of task")
    public void moveTask(@PathVariable Long taskId, @PathVariable Status newStatus) {
        taskService.moveTask(taskId, newStatus);
    }
}

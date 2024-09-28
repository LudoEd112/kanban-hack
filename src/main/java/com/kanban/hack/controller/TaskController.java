package com.kanban.hack.controller;

import com.kanban.hack.model.Status;
import com.kanban.hack.model.Task;
import com.kanban.hack.service.TaskService;
import com.kanban.hack.viewmodel.TaskVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/tasks")
@Tag(name = "Tasks", description = "Task API")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @Operation(summary = "Create", description = "Create new task")
    public Task create(@RequestBody Task task) {
        return taskService.create(task);
    }

    @GetMapping("/projects/{projectId}")
    @Operation(summary = "Get tasks", description = "Get all tasks by projectId")
    public List<Task> getAllTasksByProjectId(@PathVariable Long projectId){
        return taskService.getAllTasksByProjectId(projectId);
    }

    @GetMapping("/projects/{projectId}/tasks/{status}")
    @Operation(summary = "Get tasks", description = "Get all tasks by projectId and status")
    public List<Task> getAllTasksByProjectIdAndStatus(@PathVariable Long projectId, @PathVariable Status status){
        return taskService.getAllTasksByProjectIdAndStatus(projectId,status);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task", description = "get task by id")
    public Task getTaskByTaskId(@PathVariable Long taskId){
        return taskService.getTaskByTaskId(taskId);
    }

    @PatchMapping("/{taskId}")
    @Operation(summary = "update task", description = "Update task by taskId")
    public Task updateTask(@PathVariable Long taskId, @RequestBody Task task){
        return taskService.updateTask(taskId, task);
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "delete task", description = "Delete task by taskId")
    public void deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get task by status", description = "Get All tasks by status")
    public List<Task> getAllTasksByStatus(@PathVariable Status status){
        return taskService.getAllTasksByStatus(status);
    }

    /*@PutMapping("/{taskId}/move/{newStatus}")
    @Operation(summary = "changeStatus", description = "change status of task")
    public void moveTask(@PathVariable Long taskId, @PathVariable Status newStatus) {
        taskService.moveTask(taskId, newStatus);
    }*/
}

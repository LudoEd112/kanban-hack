package com.kanban.hack.controller;

import com.kanban.hack.model.Project;
import com.kanban.hack.model.User;
import com.kanban.hack.service.ProjectService;
import com.kanban.hack.service.TaskService;
import com.kanban.hack.viewmodel.ProjectVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/projects")
@Tag(name = "Projects", description = "Projects API")
public class ProjectController {

    @Autowired
    private AuthController authController;

    @Autowired
    public void setAuthController(AuthController authController) {
        this.authController = authController;
    }

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @PostMapping
    @Operation(summary = "Create new project", description = "Creating new project")
    public Project create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @GetMapping
    @Operation(summary = "Projects", description = "Get all projects")
    public List<ProjectVM> list() {
        User user = authController.getAuthorisedUser();
        if (user == null){
            return null;
        }
        List<Project> projects = projectService.listProjects();

        List<ProjectVM> projectVMS = projects.stream().map(temp -> {
            ProjectVM obj = new ProjectVM();
            obj.setId(temp.getId());
            obj.setTitle(temp.getTitle());
            obj.setDescription(temp.getDescription());
            return obj;
        }).collect(Collectors.toList());

        return projectVMS;
    }

    @PatchMapping("/{projectId}")
    @Operation(summary = "Patch projects", description = "change some projects fields")
    public Project updateProject(@PathVariable Long projectId, @RequestBody Project project){
        return projectService.updateProject(projectId, project);
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "Delete project", description = "delete project by projectId")
    public void deleteProject(@PathVariable Long projectId){
        projectService.deleteProject(projectId);
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get project by id", description = "get project by id")
    public Project getProjectByProjectId(@PathVariable Long projectId){
        return projectService.getProjectByProjectId(projectId);
    }


    /*@GetMapping("/boardByUserId/{user}")
    public List<ProjectVM> boardsByUserId(@PathVariable User user) {
        Long userId = user.getId();
        List<Project> boards = projectService.findByProjectId(userId);

        List<ProjectVM> projectVMS = boards.stream().map(board -> {
            ProjectVM projectVM = new ProjectVM();
            projectVM.setId(board.getId());
            projectVM.setTitle(board.getTitle());
            return projectVM;
        }).collect(Collectors.toList());

        return projectVMS;
    }*/

    /*@GetMapping("/{projectId}/backlog")
    @Operation(summary = "list tasks", description = "list tasks by projectId")
    public List<TaskVM> listByTask(@PathVariable Long projectId) {
        List<Task> tasks = taskService.listByBoard(projectId);

        List<TaskVM> taskVMS = tasks.stream().map(task -> {
            TaskVM taskVM = new TaskVM();
            taskVM.setId(task.getId());
            taskVM.setTitle(task.getTitle());
            taskVM.setDate(task.getDate());
            taskVM.setStatus(task.getStatus());
            taskVM.setProjectId(task.getProject().getId());
            taskVM.setSprintId(task.getSprint().getId());
            taskVM.setOwnerId(task.getOwner().getId());
            return taskVM;
        }).collect(Collectors.toList());

        return taskVMS;
    }*/
}

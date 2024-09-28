package com.kanban.hack.service;

import com.kanban.hack.model.Project;
import com.kanban.hack.model.Status;
import com.kanban.hack.model.Task;
import com.kanban.hack.repository.ProjectRepository;
import com.kanban.hack.repository.SprintRepository;
import com.kanban.hack.repository.TaskRepository;
import com.kanban.hack.repository.UserRepository;
import com.kanban.hack.viewmodel.TaskVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private UserRepository userRepository;

    public Task create(Task task) {
        Task taskToSave = new Task();
        taskToSave.setTitle(task.getTitle());
        taskToSave.setId(task.getId());
        taskToSave.setCreatorId(task.getCreatorId());
        taskToSave.setDescription(task.getDescription());
        taskToSave.setProjectId(task.getProjectId());
        taskToSave.setStatus(task.getStatus());
        //Project savedProject = projectRepository.findById(taskVM.getProjectId()).get();
        //taskToSave.setProject(savedProject);
        return taskRepository.save(taskToSave);
    }

    /*public List<Task> listBySprint(Long sprintId) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if (sprint.isPresent()) {
            List<Status> activeStatusList = List.of(Status.TODO, Status.DOING, Status.DONE);
            return taskRepository.findAllBySprintIdAndStatusIn(sprintId, activeStatusList);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }*/

    public void moveTask(Long taskId, Status newStatus) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            Task savedTask = task.get();
            savedTask.setStatus(newStatus);
            taskRepository.save(savedTask);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public List<Task> getAllTasksByProjectId(Long projectId) {
        return taskRepository.findAllByProjectId(projectId);

        /*List<Task> result = new ArrayList<>();
        taskRepository.findAll().iterator().forEachRemaining(result::add);
        List<Task> projectVMS = result.stream().map(temp -> {
            Task obj = new Task();
            obj.setProjectId(temp.getProjectId());
            obj.setCreatorId(temp.getCreatorId());
            obj.setTitle(temp.getTitle());
            obj.setDescription(temp.getDescription());
            obj.setStatus(temp.getStatus());
            return obj;
        }).collect(Collectors.toList());
        return result;*/
    }

    public Task getTaskByTaskId(Long taskId) {
        return taskRepository.findById(taskId).get();
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Task task = taskRepository.findById(taskId).get();
        if (updatedTask.getId() != null){
            task.setId(updatedTask.getId());
        }
        if (updatedTask.getProjectId() != null){
            task.setProjectId(updatedTask.getProjectId());
        }
        if (updatedTask.getCreatorId() != null){
            task.setCreatorId(updatedTask.getCreatorId());
        }
        if (updatedTask.getTitle() != null){
            task.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getStatus() != null){
            task.setStatus(updatedTask.getStatus());
        }
        return taskRepository.save(task);
    }

    /*public List<Task> listByBoard(Long boardId) {
        Optional<Project> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            return taskRepository.findAllByBoardIdAndStatus(boardId, Status.BACKLOG);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }*/

}

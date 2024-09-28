package com.kanban.hack.service;

import com.kanban.hack.model.Project;
import com.kanban.hack.model.Sprint;
import com.kanban.hack.model.Status;
import com.kanban.hack.model.Task;
import com.kanban.hack.model.User;
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

    public Task create(TaskVM taskVM) {
        Task taskToSave = new Task();
        taskToSave.setTitle(taskVM.getTitle());
        taskToSave.setId(taskVM.getId());
        taskToSave.setCreatorId(taskVM.getCreatorId());
        taskToSave.setDescription(taskVM.getDescription());
        taskToSave.setProjectId(taskVM.getProjectId());
        taskToSave.setStatus(taskVM.getStatus());
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

    public List<Task> getAllTasks(Long taskId) {
        List<Task> result = new ArrayList<>();
        taskRepository.findAll().iterator().forEachRemaining(result::add);
        return result;
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

package com.kanban.kanban.service;

import com.kanban.kanban.model.Board;
import com.kanban.kanban.model.Sprint;
import com.kanban.kanban.model.Status;
import com.kanban.kanban.model.Task;
import com.kanban.kanban.model.User;
import com.kanban.kanban.repository.BoardRepository;
import com.kanban.kanban.repository.SprintRepository;
import com.kanban.kanban.repository.TaskRepository;
import com.kanban.kanban.repository.UserRepository;
import com.kanban.kanban.viewmodel.TaskVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private UserRepository userRepository;

    public void create(TaskVM taskVM) {
        Task taskToSave = new Task();
        taskToSave.setTitle(taskVM.getTitle());
        taskToSave.setDate(taskVM.getDate());
        taskToSave.setStatus(taskVM.getStatus());
        taskToSave.setColor(taskVM.getColor());

        Board savedBoard = boardRepository.findById(taskVM.getBoardId()).get();
        taskToSave.setBoard(savedBoard);

        Sprint savedSprint = sprintRepository.findById(taskVM.getSprintId()).get();
        taskToSave.setSprint(savedSprint);

        User savedUser = userRepository.findById(taskVM.getOwnerId()).get();
        taskToSave.setOwner(savedUser);

        taskRepository.save(taskToSave);
    }

    public List<Task> listBySprint(Long sprintId) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if (sprint.isPresent()) {
            List<Status> activeStatusList = List.of(Status.TODO, Status.DOING, Status.DONE);
            return taskRepository.findAllBySprintIdAndStatusIn(sprintId, activeStatusList);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }

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

    public List<Task> listByBoard(Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            return taskRepository.findAllByBoardIdAndStatus(boardId, Status.BACKLOG);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}

package com.kanban.kanban.controller;

import com.kanban.kanban.model.Board;
import com.kanban.kanban.model.Task;
import com.kanban.kanban.service.BoardService;
import com.kanban.kanban.service.TaskService;
import com.kanban.kanban.viewmodel.BoardVM;
import com.kanban.kanban.viewmodel.TaskVM;
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
@RequestMapping(value = "/v1/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private TaskService taskService;

    @PostMapping
    public void create(@RequestBody BoardVM boardVM) {
        boardService.create(boardVM);
    }

    @GetMapping
    public List<BoardVM> list() {
        List<Board> boards = boardService.listBoards();

        List<BoardVM> boardVMS = boards.stream().map(temp -> {
            BoardVM obj = new BoardVM();
            obj.setId(temp.getId());
            obj.setTitle(temp.getTitle());
            return obj;
        }).collect(Collectors.toList());

        return boardVMS;
    }

   /* @GetMapping("/boardByUserId/{userId}")
    public List<BoardVM> boardByUserId(@PathVariable Long userId) {
        List<Board> boards = boardService.findByBoardId(userId);

        List<BoardVM> boardVMS = boards.stream().map(board -> {
            BoardVM boardVM = new BoardVM();
            boardVM.setId(board.getId());
            boardVM.setTitle(board.getTitle());
            return boardVM;
        }).collect(Collectors.toList());

        return boardVMS;
    }*/

    @GetMapping("/{boardId}/backlog")
    public List<TaskVM> listByTask(@PathVariable Long boardId) {
        List<Task> tasks = taskService.listByBoard(boardId);

        List<TaskVM> taskVMS = tasks.stream().map(task -> {
            TaskVM taskVM = new TaskVM();
            taskVM.setId(task.getId());
            taskVM.setTitle(task.getTitle());
            taskVM.setDate(task.getDate());
            taskVM.setStatus(task.getStatus());
            taskVM.setBoardId(task.getBoard().getId());
            taskVM.setSprintId(task.getSprint().getId());
            taskVM.setOwnerId(task.getOwner().getId());
            return taskVM;
        }).collect(Collectors.toList());

        return taskVMS;
    }
}

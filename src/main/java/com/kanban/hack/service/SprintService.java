package com.kanban.hack.service;

import com.kanban.hack.model.Board;
import com.kanban.hack.model.Sprint;
import com.kanban.hack.repository.BoardRepository;
import com.kanban.hack.repository.SprintRepository;
import com.kanban.hack.viewmodel.SprintVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private BoardRepository boardRepository;

    public void create(SprintVM sprintVM) {
        Sprint sprintToSave = new Sprint();
        Board savedBoard = boardRepository.findById(sprintVM.getBoardId()).get();
        sprintToSave.setBoard(savedBoard);
        sprintToSave.setStartDate(sprintVM.getStartDate());
        sprintToSave.setEndDate(sprintVM.getEndDate());
        sprintRepository.save(sprintToSave);
    }

    public List<Sprint> listByBoard(Long boardID) {
        Optional<Board> board = boardRepository.findById(boardID);
        if (board.isPresent()) {
            return sprintRepository.findAllByBoardIdOrderByStartDateDesc(boardID);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Board not found"
            );
        }
    }
}

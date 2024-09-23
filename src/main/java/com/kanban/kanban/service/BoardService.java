package com.kanban.kanban.service;

import com.kanban.kanban.model.Board;
import com.kanban.kanban.repository.BoardRepository;
import com.kanban.kanban.viewmodel.BoardVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void create(BoardVM boardVM) {
        Board boardToSave = new Board();
        boardToSave.setTitle(boardVM.getTitle());
        boardRepository.save(boardToSave);
    }

    /*public List<Board> findByBoardId(Long boardId){
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isPresent()){
            return boardRepository.findAllByBoardId(boardId);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Board not found"
            );
        }
    }*/

    public List<Board> listBoards() {
        List<Board> result = new ArrayList<>();
        boardRepository.findAll().iterator().forEachRemaining(result::add);
        return result;
    }
}

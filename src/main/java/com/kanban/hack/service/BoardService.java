package com.kanban.hack.service;

import com.kanban.hack.model.Board;
import com.kanban.hack.repository.BoardRepository;
import com.kanban.hack.viewmodel.BoardVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

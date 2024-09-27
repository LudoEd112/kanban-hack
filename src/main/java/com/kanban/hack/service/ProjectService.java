package com.kanban.hack.service;

import com.kanban.hack.model.Project;
import com.kanban.hack.repository.ProjectRepository;
import com.kanban.hack.viewmodel.ProjectVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public void create(ProjectVM projectVM) {
        Project projectToSave = new Project();
        projectToSave.setTitle(projectVM.getTitle());
        projectRepository.save(projectToSave);
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

    public List<Project> listProjects() {
        List<Project> result = new ArrayList<>();
        projectRepository.findAll().iterator().forEachRemaining(result::add);
        return result;
    }
}

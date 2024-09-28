package com.kanban.hack.service;

import com.kanban.hack.model.Project;
import com.kanban.hack.model.Task;
import com.kanban.hack.model.User;
import com.kanban.hack.repository.ProjectRepository;
import com.kanban.hack.repository.UserRepository;
import com.kanban.hack.viewmodel.ProjectVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public void create(ProjectVM projectVM) {
        Project projectToSave = new Project();
        projectToSave.setTitle(projectVM.getTitle());
        projectToSave.setDescription(projectVM.getDescription());
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

   /* public List<Project> listProjects(User user) {
        Optional<User> userFromRM = userRepository.findById(user.getId());
        if (userFromRM.isPresent()) {
            return projectRepository.loadProjectsByUserId(user.getId());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }*/
}

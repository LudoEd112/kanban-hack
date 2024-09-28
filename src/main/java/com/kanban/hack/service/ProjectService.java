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

    public Project create(Project project) {
        Project projectToSave = new Project();
        projectToSave.setTitle(project.getTitle());
        projectToSave.setDescription(project.getDescription());
        projectToSave.setTask(project.getTask());
        return projectRepository.save(projectToSave);
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

    public Project updateProject(Long projectId, Project updatedProject) {
        Project project = projectRepository.findById(projectId).get();
        if (updatedProject.getId() != null){
            project.setId(updatedProject.getId());
        }
        if (updatedProject.getTitle() != null){
            project.setTitle(updatedProject.getTitle());
        }
        if (updatedProject.getDescription() != null){
            project.setDescription(updatedProject.getDescription());
        }
        return projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        projectRepository.delete(project);
    }

    public Project getProjectByProjectId(Long projectId) {
        return projectRepository.findById(projectId).get();
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

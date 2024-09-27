package com.kanban.hack.service;

import com.kanban.hack.model.Project;
import com.kanban.hack.model.Sprint;
import com.kanban.hack.repository.ProjectRepository;
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
    private ProjectRepository projectRepository;

    public void create(SprintVM sprintVM) {
        Sprint sprintToSave = new Sprint();
        Project savedProject = projectRepository.findById(sprintVM.getProjectId()).get();
        sprintToSave.setProject(savedProject);
        sprintToSave.setStartDate(sprintVM.getStartDate());
        sprintToSave.setEndDate(sprintVM.getEndDate());
        sprintRepository.save(sprintToSave);
    }

    public List<Sprint> listByProject(Long projectID) {
        Optional<Project> project = projectRepository.findById(projectID);
        if (project.isPresent()) {
            return sprintRepository.findAllByProjectIdOrderByStartDateDesc(projectID);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project not found"
            );
        }
    }
}

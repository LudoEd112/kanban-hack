package com.kanban.hack.service;

import com.kanban.hack.UserDetailsImpl;
import com.kanban.hack.model.Project;
import com.kanban.hack.model.User;
import com.kanban.hack.repository.ProjectRepository;
import com.kanban.hack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));
        return UserDetailsImpl.build(user);
    }

    public UserDetailsService userDetailsService() {
        return this::loadUserByUsername;
    }

    public void save(User user){
        userRepository.save(user); //реализовали метод внедренного бина
    }

    public User assignProjectToUser(Long userId, Long projectId) {
        Set<Project> projectSet = null;
        User user = userRepository.findById(userId).get();
        Project project = projectRepository.findById(projectId).get();
        projectSet = user.getAssignedProjects();
        projectSet.add(project);
        user.setAssignedProjects(projectSet);
        return userRepository.save(user);
    }
}

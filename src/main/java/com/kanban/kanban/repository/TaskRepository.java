package com.kanban.kanban.repository;

import com.kanban.kanban.model.Status;
import com.kanban.kanban.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllBySprintIdAndStatusIn(Long sprintId, List<Status> activeStatusList);
    List<Task> findAllByBoardIdAndStatus(Long boardId, Status status);

}


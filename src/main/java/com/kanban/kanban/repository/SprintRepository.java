package com.kanban.kanban.repository;

import com.kanban.kanban.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    List<Sprint> findAllByBoardIdOrderByStartDateDesc(Long boardId);
}

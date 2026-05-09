package com.bootcamp.tasktracker.repository;

import com.bootcamp.tasktracker.entity.TaskItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskItemRepository extends JpaRepository<TaskItem, Long> {
    List<TaskItem> findByBoardId(Long boardId);
    List<TaskItem> findByBoardIdAndStatus(Long boardId, String status);
    List<TaskItem> findByStatus(String status);
}

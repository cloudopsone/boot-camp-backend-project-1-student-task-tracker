package com.bootcamp.tasktracker.repository;

import com.bootcamp.tasktracker.entity.ProjectBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectBoardRepository extends JpaRepository<ProjectBoard, Long> {
    List<ProjectBoard> findByOwnerId(Long ownerId);
}

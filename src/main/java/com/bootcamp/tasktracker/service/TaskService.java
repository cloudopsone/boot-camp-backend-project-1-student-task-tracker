package com.bootcamp.tasktracker.service;

import com.bootcamp.tasktracker.dto.TaskRequest;
import com.bootcamp.tasktracker.dto.TaskResponse;
import com.bootcamp.tasktracker.entity.AppUser;
import com.bootcamp.tasktracker.entity.ProjectBoard;
import com.bootcamp.tasktracker.entity.TaskItem;
import com.bootcamp.tasktracker.repository.AppUserRepository;
import com.bootcamp.tasktracker.repository.ProjectBoardRepository;
import com.bootcamp.tasktracker.repository.TaskItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskService {
    
    private final TaskItemRepository taskRepository;
    private final ProjectBoardRepository boardRepository;
    private final AppUserRepository userRepository;
    
    public List<TaskResponse> getAllTasks() {
        log.info("Fetching all tasks");
        return taskRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public List<TaskResponse> getTasksByBoard(Long boardId) {
        log.info("Fetching tasks for board: {}", boardId);
        return taskRepository.findByBoardId(boardId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public List<TaskResponse> getTasksByStatus(String status) {
        log.info("Fetching tasks with status: {}", status);
        return taskRepository.findByStatus(status).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public List<TaskResponse> getTasksByBoardAndStatus(Long boardId, String status) {
        log.info("Fetching tasks for board: {} with status: {}", boardId, status);
        return taskRepository.findByBoardIdAndStatus(boardId, status).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public TaskResponse getTaskById(Long id) {
        log.info("Fetching task: {}", id);
        TaskItem task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
        return toResponse(task);
    }
    
    @Transactional
    public TaskResponse createTask(TaskRequest request, Long userId) {
        log.info("Creating task: {}", request.getTitle());
        
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        
        ProjectBoard board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found: " + request.getBoardId()));
        
        TaskItem task = TaskItem.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .dueDate(request.getDueDate())
                .board(board)
                .createdBy(user)
                .build();
        
        TaskItem saved = taskRepository.save(task);
        log.info("Task created with ID: {}", saved.getId());
        return toResponse(saved);
    }
    
    @Transactional
    public TaskResponse updateTask(Long id, 
        TaskRequest request) {
        log.info("Updating task: {}", id);
        
        TaskItem task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
        
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        
        TaskItem updated = taskRepository.save(task);
        log.info("Task updated: {}", id);
        return toResponse(updated);
    }
    
    @Transactional
    public void deleteTask(Long id) {
      log.info("Deleting task: {}", id);
        if (!taskRepository.existsById(id)) {
          throw new RuntimeException("Task not found: " + id);
        }
      taskRepository.deleteById(id);
      log.info("Task deleted: {}", id);
    }
    
    private TaskResponse toResponse(TaskItem task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .boardId(task.getBoard().getId())
                .boardName(task.getBoard().getName())
                .createdById(task.getCreatedBy().getId())
                .createdByName(task.getCreatedBy().getFullName())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}

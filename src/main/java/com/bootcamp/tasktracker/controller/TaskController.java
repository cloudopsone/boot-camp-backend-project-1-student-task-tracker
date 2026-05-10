package com.bootcamp.tasktracker.controller;

import com.bootcamp.tasktracker.dto.TaskRequest;
import com.bootcamp.tasktracker.dto.TaskResponse;
import com.bootcamp.tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    private final TaskService taskService;
    
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @RequestParam(required = false) Long boardId,
            @RequestParam(required = false) String status) {
        log.info("GET /api/tasks - boardId: {}, status: {}", boardId, status);
        
        List<TaskResponse> tasks;
        if (boardId != null && status != null && !status.isEmpty()) {
            // Filter by both boardId and status
            tasks = taskService.getTasksByBoardAndStatus(boardId, status);
        } else if (boardId != null) {
            // Filter by boardId only
            tasks = taskService.getTasksByBoard(boardId);
        } else if (status != null && !status.isEmpty()) {
            // Filter by status only
            tasks = taskService.getTasksByStatus(status);
        } else {
            // Get all tasks
            tasks = taskService.getAllTasks();
        }
        
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        log.info("GET /api/tasks/{}", id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
    
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        log.info("POST /api/tasks");
        // For demo purposes, using hardcoded userId = 1
        TaskResponse response = taskService.createTask(request, 1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {
        log.info("PUT /api/tasks/{}", id);
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.info("DELETE /api/tasks/{}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

package com.bootcamp.tasktracker.service;

import com.bootcamp.tasktracker.dto.TaskResponse;
import com.bootcamp.tasktracker.repository.TaskItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    
    @Mock
    private TaskItemRepository taskRepository;
    
    @InjectMocks
    private TaskService taskService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of());
        
        List<TaskResponse> tasks = taskService.getAllTasks();
        assertEquals(0, tasks.size());
    }
    
    @Test
    void testGetTaskByIdNotFound() {
        when(taskRepository.findById(999L)).thenReturn(java.util.Optional.empty());
        
        assertThrows(RuntimeException.class, () -> taskService.getTaskById(999L));
    }
}

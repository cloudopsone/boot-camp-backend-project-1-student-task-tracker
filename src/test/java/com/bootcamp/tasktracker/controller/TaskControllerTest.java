package com.bootcamp.tasktracker.controller;

import com.bootcamp.tasktracker.dto.TaskRequest;
import com.bootcamp.tasktracker.dto.TaskResponse;
import com.bootcamp.tasktracker.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
        @MockitoBean
    private TaskService taskService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private TaskResponse sampleTask;
    
    @BeforeEach
    void setUp() {
        sampleTask = TaskResponse.builder()
                .id(1L)
                .title("Sample Task")
                .description("Sample Description")
                .status("OPEN")
                .dueDate(LocalDate.of(2026, 5, 20))
                .boardId(1L)
                .boardName("Project Board")
                .createdById(1L)
                .createdByName("John Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    @Test
    void testGetAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(sampleTask));
        
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Sample Task"));
    }
    
    @Test
    void testGetTaskById() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(sampleTask);
        
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Task"));
    }
    
    @Test
    void testCreateTask() throws Exception {
        TaskRequest request = TaskRequest.builder()
                .title("New Task")
                .description("Description")
                .status("OPEN")
                .boardId(1L)
                .build();
        
        when(taskService.createTask(any(TaskRequest.class), anyLong())).thenReturn(sampleTask);
        
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Sample Task"));
    }
    
    @Test
    void testUpdateTask() throws Exception {
        TaskRequest request = TaskRequest.builder()
                .title("Updated Task")
                .description("Updated Description")
                .status("IN_PROGRESS")
                .boardId(1L)
                .build();
        
        when(taskService.updateTask(anyLong(), any(TaskRequest.class))).thenReturn(sampleTask);
        
        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Task"));
    }
    
    @Test
    void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }
}

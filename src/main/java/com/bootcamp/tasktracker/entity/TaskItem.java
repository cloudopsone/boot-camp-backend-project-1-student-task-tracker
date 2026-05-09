package com.bootcamp.tasktracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 150)
    private String title;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false, length = 30)
    private String status;
    
    @Column
    private LocalDate dueDate;
    
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private ProjectBoard board;
    
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private AppUser createdBy;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @jakarta.persistence.PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @jakarta.persistence.PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

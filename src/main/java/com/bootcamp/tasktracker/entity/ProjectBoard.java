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
import java.time.LocalDateTime;

@Entity
@Table(name = "project_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectBoard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 150)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private AppUser owner;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @jakarta.persistence.PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

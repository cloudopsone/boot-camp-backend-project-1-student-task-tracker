package com.bootcamp.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponse {
    
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private String ownerName;
    private LocalDateTime createdAt;
}

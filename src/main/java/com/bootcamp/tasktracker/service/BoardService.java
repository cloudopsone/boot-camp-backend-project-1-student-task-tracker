package com.bootcamp.tasktracker.service;

import com.bootcamp.tasktracker.dto.BoardRequest;
import com.bootcamp.tasktracker.dto.BoardResponse;
import com.bootcamp.tasktracker.entity.AppUser;
import com.bootcamp.tasktracker.entity.ProjectBoard;
import com.bootcamp.tasktracker.repository.AppUserRepository;
import com.bootcamp.tasktracker.repository.ProjectBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardService {
    
    private final ProjectBoardRepository boardRepository;
    private final AppUserRepository userRepository;
    
    public BoardService(ProjectBoardRepository boardRepository,
                       AppUserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }
    
    public List<BoardResponse> getAllBoards() {
        log.info("Fetching all boards");
        return boardRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public BoardResponse getBoardById(Long id) {
        log.info("Fetching board: {}", id);
        ProjectBoard board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found: " + id));
        return toResponse(board);
    }
    
    @Transactional
    public BoardResponse createBoard(BoardRequest request, Long userId) {
        log.info("Creating board: {}", request.getName());
        
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        
        ProjectBoard board = ProjectBoard.builder()
                .name(request.getName())
                .description(request.getDescription())
                .owner(user)
                .build();
        
        ProjectBoard saved = boardRepository.save(board);
        log.info("Board created with ID: {}", saved.getId());
        return toResponse(saved);
    }
    
    @Transactional
    public BoardResponse updateBoard(Long id, BoardRequest request) {
        log.info("Updating board: {}", id);
        
        ProjectBoard board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found: " + id));
        
        board.setName(request.getName());
        board.setDescription(request.getDescription());
        
        ProjectBoard updated = boardRepository.save(board);
        log.info("Board updated: {}", id);
        return toResponse(updated);
    }
    
    @Transactional
    public void deleteBoard(Long id) {
        log.info("Deleting board: {}", id);
        
        if (!boardRepository.existsById(id)) {
            throw new RuntimeException("Board not found: " + id);
        }
        
        boardRepository.deleteById(id);
        log.info("Board deleted: {}", id);
    }
    
    private BoardResponse toResponse(ProjectBoard board) {
        return BoardResponse.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .ownerId(board.getOwner().getId())
                .ownerName(board.getOwner().getFullName())
                .createdAt(board.getCreatedAt())
                .build();
    }
}

package com.bootcamp.tasktracker.controller;

import com.bootcamp.tasktracker.dto.BoardRequest;
import com.bootcamp.tasktracker.dto.BoardResponse;
import com.bootcamp.tasktracker.service.BoardService;
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
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        log.info("GET /api/boards");
        return ResponseEntity.ok(boardService.getAllBoards());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Long id) {
        log.info("GET /api/boards/{}", id);
        return ResponseEntity.ok(boardService.getBoardById(id));
    }
    
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody BoardRequest request) {
        log.info("POST /api/boards");
        // For demo purposes, using hardcoded userId = 1
        BoardResponse response = boardService.createBoard(request, 1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(
            @PathVariable Long id,
            @Valid @RequestBody BoardRequest request) {
        log.info("PUT /api/boards/{}", id);
        return ResponseEntity.ok(boardService.updateBoard(id, request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        log.info("DELETE /api/boards/{}", id);
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}

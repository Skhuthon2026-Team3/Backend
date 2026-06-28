package com.example.replay.domain.memory.controller;

import com.example.replay.domain.memory.dto.MemoryCreateRequest;
import com.example.replay.domain.memory.dto.MemoryDetailResponse;
import com.example.replay.domain.memory.dto.MemoryListResponse;
import com.example.replay.domain.memory.dto.MemoryResponse;
import com.example.replay.domain.memory.service.MemoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Memory", description = "Memory API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memories")
public class MemoryController {

    private final MemoryService memoryService;

    @Operation(summary = "Create memory", description = "Save a memory with selected music information.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemoryResponse createMemory(@Valid @RequestBody MemoryCreateRequest request) {
        return memoryService.createMemory(request);
    }

    @Operation(summary = "Get my memories", description = "Get memories written by the current member.")
    @GetMapping("/me")
    public List<MemoryListResponse> getMyMemories(@RequestParam Long memberId) {
        // TODO: Replace memberId request parameter with authenticated member when login is implemented.
        return memoryService.getMyMemories(memberId);
    }

    @Operation(summary = "Get my memory detail", description = "Get a saved memory detail by id.")
    @GetMapping("/{memoryId}")
    public MemoryDetailResponse getMyMemoryDetail(@PathVariable Long memoryId, @RequestParam Long memberId) {
        // TODO: Replace memberId request parameter with authenticated member and verify ownership when login is implemented.
        return memoryService.getMyMemoryDetail(memoryId, memberId);
    }

    @Operation(summary = "Delete memory", description = "Delete a saved memory by id.")
    @DeleteMapping("/{memoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMemory(@PathVariable Long memoryId, @RequestParam Long memberId) {
        memoryService.deleteMemory(memoryId, memberId);
    }
}
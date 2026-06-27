package com.example.replay.domain.memory.controller;

import com.example.replay.domain.memory.dto.MemoryCreateRequest;
import com.example.replay.domain.memory.dto.MemoryResponse;
import com.example.replay.domain.memory.service.MemoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
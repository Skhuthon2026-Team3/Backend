package com.example.replay.domain.memory.controller;

import com.example.replay.common.response.ApiResponse;
import com.example.replay.domain.memory.dto.MemoryCreateRequest;
import com.example.replay.domain.memory.dto.MemoryDetailResponse;
import com.example.replay.domain.memory.dto.MemoryListResponse;
import com.example.replay.domain.memory.dto.MemoryResponse;
import com.example.replay.domain.memory.service.MemoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create memory", description = "Save a memory with selected music information.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemoryResponse> createMemory(
            @AuthenticationPrincipal Long memberId,
            @Valid @RequestBody MemoryCreateRequest request
    ) {
        return ApiResponse.success(memoryService.createMemory(memberId, request));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get my memories", description = "Get memories written by the current member.")
    @GetMapping("/me")
    public ApiResponse<List<MemoryListResponse>> getMyMemories(@AuthenticationPrincipal Long memberId) {
        return ApiResponse.success(memoryService.getMyMemories(memberId));
    }

    @Operation(summary = "Get recent public memories", description = "Get the latest 3 public memories for the main page.")
    @GetMapping("/recent")
    public ApiResponse<List<MemoryListResponse>> getRecentPublicMemories() {
        return ApiResponse.success(memoryService.getRecentPublicMemories());
    }

    @Operation(summary = "Get public memory detail", description = "Get a public memory detail without login.")
    @GetMapping("/public/{memoryId}")
    public ApiResponse<MemoryDetailResponse> getPublicMemoryDetail(@PathVariable Long memoryId) {
        return ApiResponse.success(memoryService.getPublicMemoryDetail(memoryId));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get my memory detail", description = "Get a saved memory detail by id.")
    @GetMapping("/{memoryId}")
    public ApiResponse<MemoryDetailResponse> getMyMemoryDetail(
            @AuthenticationPrincipal Long memberId,
            @PathVariable Long memoryId
    ) {
        return ApiResponse.success(memoryService.getMyMemoryDetail(memoryId, memberId));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete memory", description = "Delete a saved memory by id.")
    @DeleteMapping("/{memoryId}")
    public ApiResponse<Void> deleteMemory(@AuthenticationPrincipal Long memberId, @PathVariable Long memoryId) {
        memoryService.deleteMemory(memoryId, memberId);
        return ApiResponse.success();
    }
}
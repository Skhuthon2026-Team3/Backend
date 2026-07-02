package com.example.replay.domain.memory.controller;

import com.example.replay.common.response.ApiResponse;
import com.example.replay.domain.memory.dto.MemoryCreateRequest;
import com.example.replay.domain.memory.dto.MemoryDetailResponse;
import com.example.replay.domain.memory.dto.MemoryLikeResponse;
import com.example.replay.domain.memory.dto.MemoryListResponse;
import com.example.replay.domain.memory.dto.MemoryResponse;
import com.example.replay.domain.memory.dto.MemoryUpdateRequest;
import com.example.replay.domain.memory.service.MemoryLikeService;
import com.example.replay.domain.memory.service.MemoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "추억", description = "추억 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memories")
public class MemoryController {

    private final MemoryService memoryService;
    private final MemoryLikeService memoryLikeService;

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "추억 생성", description = "선택한 음악 정보와 추억 내용을 저장합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemoryResponse> createMemory(
            @AuthenticationPrincipal Long memberId,
            @Valid @RequestBody MemoryCreateRequest request
    ) {
        return ApiResponse.success(memoryService.createMemory(memberId, request));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "내 추억 목록 조회", description = "현재 로그인한 사용자가 작성한 추억 목록을 최신순으로 조회합니다.")
    @GetMapping("/me")
    public ApiResponse<List<MemoryListResponse>> getMyMemories(@AuthenticationPrincipal Long memberId) {
        return ApiResponse.success(memoryService.getMyMemories(memberId));
    }

    @Operation(summary = "최신 공개 추억 목록 조회", description = "메인 화면에 표시할 최신 공개 추억을 최대 8개까지 조회합니다.")
    @GetMapping("/recent")
    public ApiResponse<List<MemoryListResponse>> getRecentPublicMemories(@AuthenticationPrincipal Long memberId) {
        return ApiResponse.success(memoryService.getRecentPublicMemories(memberId));
    }

    @Operation(summary = "공개 추억 상세 조회", description = "로그인 없이 공개 추억의 상세 정보를 조회합니다.")
    @GetMapping("/public/{memoryId}")
    public ApiResponse<MemoryDetailResponse> getPublicMemoryDetail(
            @AuthenticationPrincipal Long memberId,
            @Parameter(description = "조회할 추억 ID", example = "1")
            @PathVariable Long memoryId
    ) {
        return ApiResponse.success(memoryService.getPublicMemoryDetail(memoryId, memberId));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "내 추억 상세 조회", description = "현재 로그인한 사용자가 작성한 추억의 상세 정보를 조회합니다.")
    @GetMapping("/{memoryId}")
    public ApiResponse<MemoryDetailResponse> getMyMemoryDetail(
            @AuthenticationPrincipal Long memberId,
            @Parameter(description = "조회할 추억 ID", example = "1")
            @PathVariable Long memoryId
    ) {
        return ApiResponse.success(memoryService.getMyMemoryDetail(memoryId, memberId));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "추억 수정", description = "현재 로그인한 사용자가 작성한 추억과 선택한 음악 정보를 수정합니다.")
    @PatchMapping("/{memoryId}")
    public ApiResponse<MemoryDetailResponse> updateMemory(
            @AuthenticationPrincipal Long memberId,
            @Parameter(description = "수정할 추억 ID", example = "1")
            @PathVariable Long memoryId,
            @Valid @RequestBody MemoryUpdateRequest request
    ) {
        return ApiResponse.success(memoryService.updateMemory(memberId, memoryId, request));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "공개 추억 좋아요", description = "현재 로그인한 사용자가 공개 추억에 좋아요를 누릅니다.")
    @PostMapping("/{memoryId}/likes")
    public ApiResponse<MemoryLikeResponse> likeMemory(
            @AuthenticationPrincipal Long memberId,
            @Parameter(description = "좋아요를 누를 추억 ID", example = "1")
            @PathVariable Long memoryId
    ) {
        return ApiResponse.success(memoryLikeService.likeMemory(memberId, memoryId));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "공개 추억 좋아요 취소", description = "현재 로그인한 사용자가 공개 추억에 누른 좋아요를 취소합니다.")
    @DeleteMapping("/{memoryId}/likes")
    public ApiResponse<MemoryLikeResponse> unlikeMemory(
            @AuthenticationPrincipal Long memberId,
            @Parameter(description = "좋아요를 취소할 추억 ID", example = "1")
            @PathVariable Long memoryId
    ) {
        return ApiResponse.success(memoryLikeService.unlikeMemory(memberId, memoryId));
    }

    @Operation(summary = "추억 좋아요 상태 조회", description = "추억의 좋아요 수와 현재 사용자의 좋아요 여부를 조회합니다. 비로그인 사용자는 likedByMe가 false로 반환됩니다.")
    @GetMapping("/{memoryId}/likes/status")
    public ApiResponse<MemoryLikeResponse> getLikeStatus(
            @AuthenticationPrincipal Long memberId,
            @Parameter(description = "좋아요 상태를 조회할 추억 ID", example = "1")
            @PathVariable Long memoryId
    ) {
        return ApiResponse.success(memoryLikeService.getLikeStatus(memberId, memoryId));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "추억 삭제", description = "현재 로그인한 사용자가 작성한 추억을 삭제합니다.")
    @DeleteMapping("/{memoryId}")
    public ApiResponse<Void> deleteMemory(
            @AuthenticationPrincipal Long memberId,
            @Parameter(description = "삭제할 추억 ID", example = "1")
            @PathVariable Long memoryId
    ) {
        memoryService.deleteMemory(memoryId, memberId);
        return ApiResponse.success();
    }
}
package com.cheongmaru.domain.search.controller;

import com.cheongmaru.domain.search.dto.SearchHistoryResponse;
import com.cheongmaru.domain.search.dto.SearchHistorySaveRequest;
import com.cheongmaru.domain.search.service.SearchHistoryService;
import com.cheongmaru.global.api.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "SearchHistory", description = "검색 기록 관련 API")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    @Operation(summary = "검색 기록 저장")
    @PostMapping
    public ApiResult<Void> save(@AuthenticationPrincipal String userId,
                                @RequestBody SearchHistorySaveRequest request) {
        searchHistoryService.save(userId, request);
        return ApiResult.success(null);
    }

    @Operation(summary = "검색 기록 전체 조회")
    @GetMapping
    public ApiResult<List<SearchHistoryResponse>> getAll(@AuthenticationPrincipal String userId) {
        return ApiResult.success(searchHistoryService.getAll(userId));
    }

    @Operation(summary = "검색 기록 단건 삭제")
    @DeleteMapping("/{id}")
    public ApiResult<Void> deleteOne(@PathVariable Long id,
                                     @AuthenticationPrincipal String userId) {
        searchHistoryService.deleteById(id, userId);
        return ApiResult.success(null);
    }
}

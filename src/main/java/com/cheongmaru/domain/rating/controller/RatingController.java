package com.cheongmaru.domain.rating.controller;

import com.cheongmaru.domain.rating.dto.RatingRequest;
import com.cheongmaru.domain.rating.service.RatingService;
import com.cheongmaru.global.api.ApiResult;
import com.cheongmaru.global.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
@Tag(name = "Rating", description = "별점 관련 API")
public class RatingController {

    private final RatingService ratingService;

    @Operation(summary = "별점 등록")
    @PostMapping
    public ApiResult<Void> createRating(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestBody RatingRequest request) {
        ratingService.create(userDetails.getUser().getId(), request);
        return ApiResult.success(null);
    }



    @Operation(summary = "별점 수정")
    @PatchMapping("/{placeId}")
    public ApiResult<Void> updateRating(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable Long placeId,
                                        @RequestBody RatingRequest request) {
        String userId = userDetails.getUser().getId();
        ratingService.update(userId, placeId, request.getScore());
        return ApiResult.success(null);
    }

    @Operation(summary = "별점 삭제")
    @DeleteMapping("/{placeId}")
    public ApiResult<Void> deleteRating(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable Long placeId) {
        String userId = userDetails.getUser().getId();
        ratingService.delete(userId, placeId);
        return ApiResult.success(null);
    }
}

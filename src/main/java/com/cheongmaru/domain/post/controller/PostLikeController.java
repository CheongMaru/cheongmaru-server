package com.cheongmaru.domain.post.controller;

import com.cheongmaru.domain.post.dto.PostResponse;
import com.cheongmaru.domain.post.service.PostLikeService;
import com.cheongmaru.global.api.ApiResult;
import com.cheongmaru.global.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "Post Like", description = "게시글 좋아요 관련 API")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "게시글 좋아요", description = "게시글에 좋아요를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 등록 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/{postId}/like")
    public ApiResult<String> likePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postLikeService.likePost(userDetails.getUsername(), postId);
        return ApiResult.success("좋아요를 등록했습니다.");
    }

    @Operation(summary = "게시글 좋아요 취소", description = "게시글에 등록된 좋아요를 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @DeleteMapping("/{postId}/like")
    public ApiResult<String> unlikePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postLikeService.unlikePost(userDetails.getUsername(), postId);
        return ApiResult.success("좋아요를 취소했습니다.");
    }

    @Operation(summary = "내가 좋아요한 게시글 조회", description = "사용자가 좋아요한 게시글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping("/me")
    public ApiResult<List<PostResponse>> getMyLikedPosts(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<PostResponse> likedPosts = postLikeService.getLikedPosts(userDetails.getUsername());
        return ApiResult.success(likedPosts);
    }
}

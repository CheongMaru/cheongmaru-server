package com.cheongmaru.domain.post.controller;

import com.cheongmaru.domain.post.dto.PostRequest;
import com.cheongmaru.domain.post.service.PostService;
import com.cheongmaru.global.api.ApiResult;
import com.cheongmaru.global.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성
     */
    @Operation(
            summary = "게시글 생성",
            description = "인증된 사용자가 게시글을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping
    public ApiResult<Long> createPost(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PostRequest request
    ) {
        Long postId = postService.createPost(userDetails.getUsername(), request);
        return ApiResult.success(postId);
    }


    /**
     * 게시글 수정
     */
    @Operation(
            summary = "게시글 수정",
            description = "본인의 게시글을 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PatchMapping("/{postId}")
    public ApiResult<String> updatePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PostRequest request
    ) {
        String message = postService.updatePost(postId, userDetails.getUsername(), request);
        return ApiResult.success(message);
    }

    /**
     * 게시글 삭제
     */
    @Operation(
            summary = "게시글 삭제",
            description = "본인의 게시글을 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @DeleteMapping("/{postId}")
    public ApiResult<String> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        String message = postService.deletePost(postId, userDetails.getUsername());
        return ApiResult.success(message);
    }

}




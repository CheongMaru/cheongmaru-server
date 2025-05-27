package com.cheongmaru.domain.post.controller;

import com.cheongmaru.domain.post.dto.PostDetailResponse;
import com.cheongmaru.domain.post.dto.PostRequest;
import com.cheongmaru.domain.post.dto.PostResponse;
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

import java.util.List;

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


    /**
     * 전체 게시글 목록 조회
     */
    @Operation(
            summary = "전체 게시글 목록 조회",
            description = "전체 게시글을 최신순으로 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공")
    })
    @GetMapping
    public ApiResult<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ApiResult.success(posts);
    }


    /**
     * 카테고리별 게시글 목록 조회
     */
    @GetMapping("/categories")
    @Operation(
            summary = "카테고리별 게시글 목록 조회",
            description = "카테고리 ID를 기준으로 게시글을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리별 게시글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 카테고리 요청")
    })
    public ApiResult<List<PostResponse>> getPostsByCategory(@RequestParam Long categoryId) {
        List<PostResponse> posts = postService.getPostsByCategory(categoryId);
        return ApiResult.success(posts);
    }


    /**
     * 게시글 검색
     */
    @GetMapping("/search")
    @Operation(
            summary = "게시글 검색",
            description = "제목 또는 내용에서 검색어가 포함된 게시글을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 결과 조회 성공"),
            @ApiResponse(responseCode = "400", description = "검색어 누락 또는 잘못된 요청")
    })
    public ApiResult<List<PostResponse>> searchPosts(@RequestParam(required = false) String query) {
        List<PostResponse> posts = postService.searchPosts(query);
        return ApiResult.success(posts);
    }



    /**
     * 게시글 상세 조회
     */
    @GetMapping("/{postId}")
    @Operation(
            summary = "게시글 상세 조회",
            description = "게시글 ID로 상세 내용을 조회하며, 조회수가 증가합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    public ApiResult<PostDetailResponse> getPostDetail(@PathVariable Long postId) {
        PostDetailResponse response = postService.getPostDetail(postId);
        return ApiResult.success(response);
    }

}




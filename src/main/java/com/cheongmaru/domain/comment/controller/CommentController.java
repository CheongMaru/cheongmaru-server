package com.cheongmaru.domain.comment.controller;

import com.cheongmaru.domain.comment.dto.CommentRequest;
import com.cheongmaru.domain.comment.dto.CommentResponse;
import com.cheongmaru.domain.comment.service.CommentService;
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
@RequestMapping("/api")
@Tag(name = "Comment", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "특정 게시글에 댓글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 작성 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 사용자 없음")
    })
    @PostMapping("/posts/{postId}/comments")
    public ApiResult<Long> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long postId,
            @RequestBody CommentRequest request
    ) {
        Long commentId = commentService.createComment(userDetails.getUsername(), postId, request);
        return ApiResult.success(commentId);
    }

    @Operation(summary = "댓글 수정", description = "본인의 댓글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "댓글 또는 사용자 없음")
    })
    @PatchMapping("/comments/{commentId}")
    public ApiResult<String> updateComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long commentId,
            @RequestBody CommentRequest request
    ) {
        commentService.updateComment(userDetails.getUsername(), commentId, request);
        return ApiResult.success("댓글이 수정되었습니다.");
    }

    @Operation(summary = "댓글 삭제", description = "본인의 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "댓글 또는 사용자 없음")
    })
    @DeleteMapping("/comments/{commentId}")
    public ApiResult<String> deleteComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(userDetails.getUsername(), commentId);
        return ApiResult.success("댓글이 삭제되었습니다.");
    }

    @Operation(summary = "게시글의 댓글 목록 조회", description = "특정 게시글에 작성된 댓글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @GetMapping("/posts/{postId}/comments")
    public ApiResult<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
        return ApiResult.success(commentService.getCommentsByPost(postId));
    }

    @Operation(summary = "내 댓글 목록 조회", description = "로그인한 사용자의 댓글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 댓글 목록 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @GetMapping("/comments/me")
    public ApiResult<List<CommentResponse>> getCommentsByUser(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResult.success(commentService.getCommentsByUser(userDetails.getUsername()));
    }
}

package com.cheongmaru.domain.comment.dto;

import com.cheongmaru.domain.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getUser().getNickname())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}

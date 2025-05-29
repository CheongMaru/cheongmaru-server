package com.cheongmaru.domain.post.dto;

import com.cheongmaru.domain.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String author;
    private String category;
    private LocalDateTime createdAt;
    private int viewCount;
    private int likeCount;
    private int commentCount;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getUser().getNickname())
                .category(post.getCategory().getName())
                .createdAt(post.getCreatedAt())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .build();
    }
}

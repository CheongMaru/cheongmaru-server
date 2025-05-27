package com.cheongmaru.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String category;
    private int viewCount;
    private int likeCount;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
}

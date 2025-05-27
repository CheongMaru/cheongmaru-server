package com.cheongmaru.domain.post.dto;

import java.time.LocalDateTime;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime createdAt;
}

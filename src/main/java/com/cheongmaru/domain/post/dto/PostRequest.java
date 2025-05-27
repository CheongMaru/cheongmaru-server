package com.cheongmaru.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true) // JSON 역직렬화 시 final 없이도 필드 초기화 허용
@AllArgsConstructor
public class PostRequest {
    private Long categoryId;
    private String title;
    private String content;
    private List<String> imageUrls;
}

package com.cheongmaru.domain.search.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "search_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(nullable = false, length = 255)
    private String keyword;

    @Column(name = "searched_at", nullable = false)
    private LocalDateTime searchedAt;

    @Builder
    public SearchHistory(String userId, String keyword, LocalDateTime searchedAt) {
        this.userId = userId;
        this.keyword = keyword;
        this.searchedAt = searchedAt;
    }

    public static SearchHistory create(String userId, String keyword) {
        return SearchHistory.builder()
                .userId(userId)
                .keyword(keyword)
                .searchedAt(LocalDateTime.now())
                .build();
    }
}

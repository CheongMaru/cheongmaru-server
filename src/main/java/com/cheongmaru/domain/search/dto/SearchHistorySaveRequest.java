package com.cheongmaru.domain.search.dto;

import com.cheongmaru.domain.search.domain.SearchHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SearchHistorySaveRequest {

    private String keyword;

    public SearchHistory toEntity(String userId) {
        return SearchHistory.builder()
                .userId(userId)
                .keyword(keyword)
                .searchedAt(LocalDateTime.now())
                .build();
    }
}

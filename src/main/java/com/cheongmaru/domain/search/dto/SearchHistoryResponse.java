package com.cheongmaru.domain.search.dto;

import com.cheongmaru.domain.search.domain.SearchHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SearchHistoryResponse {

    private Long id;
    private String keyword;
    private LocalDateTime searchedAt;

    public static SearchHistoryResponse fromEntity(SearchHistory entity) {
        return SearchHistoryResponse.builder()
                .id(entity.getId())
                .keyword(entity.getKeyword())
                .searchedAt(entity.getSearchedAt())
                .build();
    }
}

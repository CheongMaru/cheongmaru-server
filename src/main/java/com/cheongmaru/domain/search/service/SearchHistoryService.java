package com.cheongmaru.domain.search.service;

import com.cheongmaru.domain.search.domain.SearchHistory;
import com.cheongmaru.domain.search.dto.SearchHistorySaveRequest;
import com.cheongmaru.domain.search.dto.SearchHistoryResponse;
import com.cheongmaru.domain.search.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    /**
     * 검색어 저장
     */
    public void save(String userId, SearchHistorySaveRequest request) {
        SearchHistory history = SearchHistory.builder()
                .userId(userId)
                .keyword(request.getKeyword())
                .searchedAt(LocalDateTime.now())
                .build();
        searchHistoryRepository.save(history);
    }

    /**
     * 최근 검색어 최대 10개 조회
     */
    public List<SearchHistoryResponse> getAll(String userId) {
        return searchHistoryRepository.findTop10ByUserIdOrderBySearchedAtDesc(userId).stream()
                .map(SearchHistoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 검색어 삭제
     */
    public void deleteById(Long id, String userId) {
        searchHistoryRepository.findByIdAndUserId(id, userId)
                .ifPresent(searchHistoryRepository::delete);
    }
}

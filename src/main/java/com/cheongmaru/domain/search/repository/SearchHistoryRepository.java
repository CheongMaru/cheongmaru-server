package com.cheongmaru.domain.search.repository;

import com.cheongmaru.domain.search.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findTop10ByUserIdOrderBySearchedAtDesc(String userId);

    Optional<SearchHistory> findByIdAndUserId(Long id, String userId);

    void deleteByIdAndUserId(Long id, String userId);
}

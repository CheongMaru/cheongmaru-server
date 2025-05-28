package com.cheongmaru.domain.place.repository;

import com.cheongmaru.domain.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    // 특정 태그 이름으로 장소들을 조회하는 메서드
    List<Place> findByTags_Name(String tagName);
}

package com.cheongmaru.domain.place.repository;

import com.cheongmaru.domain.place.domain.Place;       // 엔티티 패키지 경로 확인!
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    // JpaRepository를 상속하면 findAll()이 자동으로 제공됩니다.
}

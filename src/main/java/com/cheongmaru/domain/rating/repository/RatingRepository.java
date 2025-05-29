package com.cheongmaru.domain.rating.repository;

import com.cheongmaru.domain.place.domain.Place;
import com.cheongmaru.domain.rating.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByUserIdAndPlace(String userId, Place place);


}

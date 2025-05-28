package com.cheongmaru.domain.rating.service;

import com.cheongmaru.domain.place.domain.Place;
import com.cheongmaru.domain.place.repository.PlaceRepository;
import com.cheongmaru.domain.rating.domain.Rating;
import com.cheongmaru.domain.rating.dto.RatingRequest;
import com.cheongmaru.domain.rating.repository.RatingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final PlaceRepository placeRepository;

    /**
     * 별점 등록
     */
    @Transactional
    public void create(String userId, RatingRequest request) {
        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장소입니다."));

        Rating rating = Rating.builder()
                .userId(userId)
                .place(place)
                .score(request.getScore())
                .createdAt(LocalDateTime.now())
                .build();
        ratingRepository.save(rating);
    }

    /**
     * 별점 수정
     */
    @Transactional
    public void update(String userId, Long placeId, int score) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장소입니다."));

        Rating rating = ratingRepository.findByUserIdAndPlace(userId, place)
                .orElseThrow(() -> new IllegalArgumentException("별점이 존재하지 않습니다."));

        rating.updateScore(score);
    }

    /**
     * 별점 삭제
     */
    @Transactional
    public void delete(String userId, Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장소입니다."));

        Rating rating = ratingRepository.findByUserIdAndPlace(userId, place)
                .orElseThrow(() -> new IllegalArgumentException("별점이 존재하지 않습니다."));

        ratingRepository.delete(rating);
    }
}

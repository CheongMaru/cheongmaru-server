package com.cheongmaru.domain.rating.dto;

import com.cheongmaru.domain.rating.domain.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RatingResponse {

    private Long id;
    private Long placeId;
    private int score;
    private LocalDateTime createdAt;

    public static RatingResponse fromEntity(Rating rating) {
        return new RatingResponse(
                rating.getId(),
                rating.getPlace().getId(),
                rating.getScore(),
                rating.getCreatedAt()
        );
    }
}

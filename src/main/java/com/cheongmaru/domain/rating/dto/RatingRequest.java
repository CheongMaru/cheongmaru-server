package com.cheongmaru.domain.rating.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RatingRequest {

    @NotNull(message = "placeId는 필수입니다.")
    private Long placeId;

    @Min(value = 1, message = "최소 별점은 1점입니다.")
    @Max(value = 5, message = "최대 별점은 5점입니다.")
    private int score;
}

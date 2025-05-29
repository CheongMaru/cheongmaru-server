package com.cheongmaru.domain.rating.domain;

import com.cheongmaru.domain.place.domain.Place;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public void updateScore(int newScore) {
        this.score = newScore;
    }
}

package com.cheongmaru.domain.location.domain;

import com.cheongmaru.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(name = "certified_at", nullable = false)
    private LocalDateTime certifiedAt;

    private Location(User user, double latitude, double longitude) {
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.certifiedAt = LocalDateTime.now();
    }

    public static Location create(User user, double lat, double lon) {
        return new Location(user, lat, lon);
    }

    public void update(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
        this.certifiedAt = LocalDateTime.now();
    }
}

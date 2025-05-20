package com.cheongmaru.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id", nullable = false, length = 36)
    private String id;

    @Column(name = "kakao_id", nullable = false, unique = true)
    private Long kakaoId;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(length = 100)
    private String email;

    @Column(name = "age_range", length = 20)
    private String ageRange;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public enum Gender {
        MALE, FEMALE
    }

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    public static User createFromKakao(Long kakaoId, String nickname, String email, String ageRange, String gender) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .kakaoId(kakaoId)
                .nickname(nickname)
                .email(email)
                .ageRange(ageRange)
                .gender("male".equals(gender) ? Gender.MALE : Gender.FEMALE)
                .isVerified(false) // 기본 인증 false
                .createdAt(LocalDateTime.now())
                .build();
    }
}

package com.cheongmaru.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoProfileDto {
    private Long id; // kakaoId
    private KakaoAccount kakao_account;

    @Getter
    @NoArgsConstructor
    public static class KakaoAccount {
        private Profile profile;
        private String email;
        private String age_range;
        private String gender;
    }

    @Getter
    @NoArgsConstructor
    public static class Profile {
        private String nickname;
    }

    public Long getKakaoId() {
        return id;
    }

    public String getNickname() {
        return kakao_account.getProfile().getNickname();
    }

    public String getEmail() {
        return kakao_account.getEmail();
    }

    public String getAgeRange() {
        return kakao_account.getAge_range();
    }

    public String getGender() {
        return kakao_account.getGender();
    }
}

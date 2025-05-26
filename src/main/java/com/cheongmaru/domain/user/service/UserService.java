package com.cheongmaru.domain.user.service;

import com.cheongmaru.domain.user.domain.RefreshToken;
import com.cheongmaru.domain.user.domain.User;
import com.cheongmaru.domain.user.dto.KakaoProfileDto;
import com.cheongmaru.domain.user.dto.LoginResponse;
import com.cheongmaru.domain.user.dto.ReissueResponse;
import com.cheongmaru.domain.user.repository.RefreshTokenRepository;
import com.cheongmaru.domain.user.repository.UserRepository;
import com.cheongmaru.global.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse login(KakaoProfileDto profile) {
        // 1. 사용자 조회 또는 생성
        User user = userRepository.findByKakaoId(profile.getKakaoId())
                .orElseGet(() -> userRepository.save(
                        User.createFromKakao(
                                profile.getKakaoId(),
                                profile.getNickname(),
                                profile.getEmail(),
                                profile.getAgeRange(),
                                profile.getGender()
                        )
                ));

        // 2. 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        // 3. 리프레시 토큰 저장 (기존 토큰이 있으면 덮어씀)
        refreshTokenRepository.findByUserId(user.getId())
                .ifPresent(refreshTokenRepository::delete);
        refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));

        // 4. 응답 반환
        return new LoginResponse(user.getId(), accessToken, refreshToken);
    }

    @Transactional
    public ReissueResponse reissueAccessToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        String email = jwtTokenProvider.getClaims(refreshToken).getSubject();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        RefreshToken storedToken = refreshTokenRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("저장된 리프레시 토큰이 없습니다."));

        if (!storedToken.getToken().equals(refreshToken)) {
            throw new IllegalArgumentException("리프레시 토큰이 일치하지 않습니다.");
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole().name());
        return new ReissueResponse(newAccessToken);
    }

    @Transactional
    public void logout(String userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }


}

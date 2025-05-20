package com.cheongmaru.user.controller;

import com.cheongmaru.global.auth.JwtTokenProvider;
import com.cheongmaru.user.domain.User;
import com.cheongmaru.user.dto.AccessTokenDto;
import com.cheongmaru.user.dto.KakaoLoginRequest;
import com.cheongmaru.user.dto.KakaoProfileDto;
import com.cheongmaru.user.dto.LoginResponse;
import com.cheongmaru.user.service.KakaoService;
import com.cheongmaru.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final KakaoService kakaoService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestBody KakaoLoginRequest request) {
        // 1. 인가 코드 → 액세스 토큰 요청
        AccessTokenDto tokenDto = kakaoService.getAccessToken(request.getCode());

        // 2. 사용자 정보 조회
        KakaoProfileDto kakaoProfile = kakaoService.getKakaoProfile(tokenDto.getAccessToken());

        // 3. 유저 저장 or 조회
        User user = userService.findOrCreateUser(kakaoProfile);

        // 4. JWT 발급
        String accessToken = jwtTokenProvider.createToken(user.getEmail(), user.getRole().toString());
        String refreshToken = "dummy_refresh_token"; // 추후 구현 시 교체

        // 5. DTO 응답
        LoginResponse response = new LoginResponse(user.getId(), accessToken, refreshToken);
        return ResponseEntity.ok(response);
    }
}

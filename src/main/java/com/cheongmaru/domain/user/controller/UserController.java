package com.cheongmaru.domain.user.controller;

import com.cheongmaru.global.api.ApiResult;
import com.cheongmaru.global.jwt.JwtTokenProvider;
import com.cheongmaru.domain.user.domain.User;
import com.cheongmaru.domain.user.dto.AccessTokenDto;
import com.cheongmaru.domain.user.dto.KakaoLoginRequest;
import com.cheongmaru.domain.user.dto.KakaoProfileDto;
import com.cheongmaru.domain.user.dto.LoginResponse;
import com.cheongmaru.domain.user.service.KakaoService;
import com.cheongmaru.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 API")
@RequiredArgsConstructor
public class UserController {

    private final KakaoService kakaoService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(
            summary = "카카오 로그인",
            description = "카카오 인가 코드를 통해 로그인하고 JWT 토큰을 반환합니다."
    )
    @PostMapping("/kakao")
    public ApiResult<LoginResponse> kakaoLogin(@RequestBody KakaoLoginRequest request) {
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
        return ApiResult.success(response);
    }
}

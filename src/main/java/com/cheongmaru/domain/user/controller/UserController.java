package com.cheongmaru.domain.user.controller;

import com.cheongmaru.domain.user.domain.User;
import com.cheongmaru.domain.user.dto.*;
import com.cheongmaru.global.api.ApiResult;
import com.cheongmaru.domain.user.service.KakaoService;
import com.cheongmaru.domain.user.service.UserService;
import com.cheongmaru.global.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 API")
@RequiredArgsConstructor
public class UserController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @Operation(
            summary = "카카오 로그인",
            description = "카카오 인가 코드를 통해 로그인하고 JWT 토큰을 반환합니다.")
    @PostMapping("/kakao")
    public ApiResult<LoginResponse> kakaoLogin(@RequestBody KakaoLoginRequest request) {

        // 1. 인가 코드로 access token 받기
        AccessTokenDto tokenDto = kakaoService.getAccessToken(request.getCode());

        // 2. access token 으로 사용자 프로필 조회
        KakaoProfileDto kakaoProfile = kakaoService.getKakaoProfile(tokenDto.getAccessToken());

        // 3. 로그인 처리 및 토큰 발급 (UserService가 모든 처리 담당)
        LoginResponse response = userService.login(kakaoProfile);

        // 4. 응답 반환
        return ApiResult.success(response);
    }

    @Operation(summary = "AccessToken 재발급", description = "RefreshToken을 통해 AccessToken을 재발급합니다.")
    @PostMapping("/refresh")
    public ApiResult<ReissueResponse> refresh(@RequestBody ReissueRequest request) {
        return ApiResult.success(userService.reissueAccessToken(request.getRefreshToken()));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자의 refresh token을 삭제합니다.")
    public ApiResult<?> logout(@AuthenticationPrincipal User user) {
        userService.logout(user.getId());
        return ApiResult.success("로그아웃 완료");
    }

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 정보를 반환합니다.")
    @GetMapping("/me")
    public ApiResult<UserResponseDto> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        UserResponseDto response = new UserResponseDto(user.getId(), user.getEmail(), user.getNickname());
        return ApiResult.success(response);
    }

}

package com.cheongmaru.domain.user.controller;

import com.cheongmaru.global.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dev-auth")
@Tag(name = "DevAuth", description = "개발용 임시 인증 API")
@RequiredArgsConstructor
public class DevAuthController {

    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "개발용 JWT 발급", description = "입력값 없이 access token을 발급합니다 (개발 전용)")
    @PostMapping("/mock-token")
    public String issueMockAccessToken() {
        String dummyEmail = "dev@cheongmaru.com";
        String dummyRole = "USER";
        return jwtTokenProvider.createToken(dummyEmail, dummyRole);
    }
}

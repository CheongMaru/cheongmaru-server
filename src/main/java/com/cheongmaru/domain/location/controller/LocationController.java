package com.cheongmaru.domain.location.controller;

import com.cheongmaru.domain.location.dto.LocationRequest;
import com.cheongmaru.domain.location.service.LocationService;
import com.cheongmaru.global.api.ApiResult;
import com.cheongmaru.global.auth.CustomUserDetails;
import com.cheongmaru.global.exception.CustomException;
import com.cheongmaru.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
@Tag(name = "Location", description = "위치 인증 API")
public class LocationController {

    private final LocationService locationService;

    @Operation(
            summary = "위치 인증 여부 조회",
            description = "현재 로그인한 사용자가 위치 인증을 했는지 여부를 확인합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping("/certified")
    public ApiResult<Boolean> isLocationCertified(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        boolean certified = locationService.isLocationCertified(userDetails.getUsername());
        return ApiResult.success(certified);
    }

    @Operation(
            summary = "위치 인증",
            description = "위도와 경도로 청주 지역 여부를 인증합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "위치 인증 성공"),
            @ApiResponse(responseCode = "403", description = "청주 외 지역일 경우 인증 실패")
    })
    @PostMapping("/certify")
    public ApiResult<String> certifyLocation(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody LocationRequest request) {

        boolean result = locationService.certify(userDetails.getUsername(), request);

        if (!result) {
            throw new CustomException(ErrorCode.LOCATION_NOT_CERTIFIABLE);
        }

        return ApiResult.success("위치 인증이 완료되었습니다.");
    }

}


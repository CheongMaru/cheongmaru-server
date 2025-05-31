package com.cheongmaru.domain.location.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "위치 인증 요청")
public record LocationRequest(

        @Schema(description = "위도", example = "36.6285")
        double latitude,

        @Schema(description = "경도", example = "127.4561")
        double longitude

) {}

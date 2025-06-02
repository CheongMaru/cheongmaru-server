package com.cheongmaru.domain.place.controller;

import com.cheongmaru.domain.place.dto.PlaceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.cheongmaru.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@io.swagger.v3.oas.annotations.tags.Tag(name = "Place", description = "장소 관련 API")
@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @Operation(
            summary = "장소 목록 조회 (전체 또는 태그별)",
            description = "전체 장소 목록을 조회하거나, 'tag' 쿼리 파라미터로 특정 태그에 해당하는 장소 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장소 목록을 반환함"),
            @ApiResponse(responseCode = "404", description = "태그를 찾을 수 없거나 해당 태그를 가진 장소가 없음 (태그 필터링 시)"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping
    public List<PlaceDto> getPlaces(@RequestParam(value = "tag", required = false) String tagName) {
        if (tagName != null && !tagName.isEmpty()) {
            return placeService.getPlacesByTagName(tagName);
        } else {
            return placeService.getAllPlaces();
        }
    }

    @Operation(summary = "장소 상세 조회", description = "특정 ID에 해당하는 장소의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장소 상세 정보를 반환함"),
            @ApiResponse(responseCode = "404", description = "장소를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("/{placeId}")
    public PlaceDto getPlaceDetail(@PathVariable Long placeId) {
        return placeService.getPlaceDetail(placeId);
    }
}
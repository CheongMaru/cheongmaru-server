package com.cheongmaru.domain.place.controller;

import com.cheongmaru.domain.place.dto.PlaceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import com.cheongmaru.domain.place.service.PlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@io.swagger.v3.oas.annotations.tags.Tag(name = "Place", description = "장소 관련 API")
@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Operation(summary = "장소 목록 조회", description = "전체 장소 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장소 목록을 반환함"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    // 기존: 전체 장소 목록 조회
    @GetMapping
    public List<PlaceDto> listAll() {
        return placeService.getAllPlaces();
    }

    // 기존: 태그별 장소 목록 조회
    @Operation(summary = "태그별 장소 목록 조회", description = "특정 태그에 해당하는 장소 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 태그별 장소 목록을 반환함"),
            @ApiResponse(responseCode = "404", description = "태그를 찾을 수 없거나 해당 태그를 가진 장소가 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping("/tags")
    public List<PlaceDto> listByTag(@RequestParam("tag") String tagName) {
        return placeService.getPlacesByTagName(tagName);
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
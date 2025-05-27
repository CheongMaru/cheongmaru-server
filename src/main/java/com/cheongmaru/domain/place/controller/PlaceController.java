package com.cheongmaru.domain.place.controller;

import com.cheongmaru.domain.place.service.PlaceService;
import com.cheongmaru.domain.place.dto.PlaceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Place", description = "장소 관련 API")
public class PlaceController {
    private final PlaceService service;

    public PlaceController(PlaceService service) {
        this.service = service;
    }

    @Operation(summary = "장소 목록 조회", description = "전체 장소 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 장소 목록을 반환함"),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    @GetMapping
    public ResponseEntity<List<PlaceDto>> getPlaces() {
        List<PlaceDto> places = service.getAllPlaces();
        return ResponseEntity.ok(places);
    }
}

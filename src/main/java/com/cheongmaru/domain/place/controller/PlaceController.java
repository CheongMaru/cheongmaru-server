package com.cheongmaru.domain.place.controller;

import com.cheongmaru.domain.place.service.PlaceService;
import com.cheongmaru.domain.place.service.dto.PlaceDto;
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

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getPlaces() {
        List<PlaceDto> places = service.getAllPlaces();
        return ResponseEntity.ok(places);
    }
}

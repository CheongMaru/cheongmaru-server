package com.cheongmaru.domain.place.service;

import com.cheongmaru.domain.place.repository.PlaceRepository;
import com.cheongmaru.domain.place.dto.PlaceDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    private final PlaceRepository repo;

    public PlaceService(PlaceRepository repo) {
        this.repo = repo;
    }

    public List<PlaceDto> getAllPlaces() {
        return repo.findAll().stream()
                .map(PlaceDto::fromEntity)
                .collect(Collectors.toList());
    }

}

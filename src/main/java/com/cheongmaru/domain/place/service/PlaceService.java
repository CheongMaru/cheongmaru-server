package com.cheongmaru.domain.place.service;

import com.cheongmaru.domain.place.domain.Place;
import com.cheongmaru.domain.place.dto.PlaceDto;
import com.cheongmaru.domain.place.repository.PlaceRepository;
import com.cheongmaru.domain.tag.domain.Tag;
import com.cheongmaru.domain.tag.repository.TagRepository;
import com.cheongmaru.global.exception.CustomException;
import com.cheongmaru.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // import 추가

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final TagRepository tagRepository;

    public PlaceService(PlaceRepository placeRepository, TagRepository tagRepository) {
        this.placeRepository = placeRepository;
        this.tagRepository = tagRepository;
    }

    // 기존: 전체 장소 목록 조회
    @Transactional(readOnly = true) // <-- 이 어노테이션 추가
    public List<PlaceDto> getAllPlaces() {
        return placeRepository.findAll()
                .stream()
                .map(PlaceDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 기존: 태그 이름으로 장소 목록 조회
    @Transactional(readOnly = true) // <-- 이 어노테이션 추가
    public List<PlaceDto> getPlacesByTagName(String tagName) {
        tagRepository.findByName(tagName)
                .orElseThrow(() -> new CustomException(ErrorCode.TAG_NOT_FOUND));

        List<Place> places = placeRepository.findByTags_Name(tagName);

        if (places.isEmpty()) {
            throw new CustomException(ErrorCode.PLACE_NOT_FOUND_BY_TAG);
        }

        return places.stream()
                .map(PlaceDto::fromEntity)
                .collect(Collectors.toList());
    }


    /**
     * 장소 상세 조회
     */
    @Transactional(readOnly = true) // <-- 이 어노테이션 추가
    public PlaceDto getPlaceDetail(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));
        return PlaceDto.fromEntity(place);
    }

}
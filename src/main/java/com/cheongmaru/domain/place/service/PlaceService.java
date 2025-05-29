package com.cheongmaru.domain.place.service;

import com.cheongmaru.domain.place.domain.Place;
import com.cheongmaru.domain.place.dto.PlaceDto;
import com.cheongmaru.domain.place.repository.PlaceRepository;
import com.cheongmaru.domain.tag.domain.Tag; // Tag import 추가
import com.cheongmaru.domain.tag.repository.TagRepository; // TagRepository import 추가
import com.cheongmaru.global.exception.CustomException; // CustomException import 추가
import com.cheongmaru.global.exception.ErrorCode; // ErrorCode import 추가
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final TagRepository tagRepository; // TagRepository 주입 추가

    public PlaceService(PlaceRepository placeRepository, TagRepository tagRepository) { // 생성자 수정
        this.placeRepository = placeRepository;
        this.tagRepository = tagRepository;
    }

    // 기존: 전체 장소 목록 조회
    public List<PlaceDto> getAllPlaces() {
        return placeRepository.findAll()
                .stream()
                .map(PlaceDto::fromEntity)
                .collect(Collectors.toList());
    }

    // ✅ 수정: 태그 이름으로 장소 목록 조회
    public List<PlaceDto> getPlacesByTagName(String tagName) {
        // 1. 태그 존재 여부 확인
        tagRepository.findByName(tagName)
                .orElseThrow(() -> new CustomException(ErrorCode.TAG_NOT_FOUND));

        // 2. 해당 태그를 가진 장소 조회
        List<Place> places = placeRepository.findByTags_Name(tagName);

        // 3. 조회된 장소가 없는 경우 예외 발생
        if (places.isEmpty()) {
            throw new CustomException(ErrorCode.PLACE_NOT_FOUND_BY_TAG);
        }

        return places.stream()
                .map(PlaceDto::fromEntity)
                .collect(Collectors.toList());
    }
}
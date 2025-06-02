package com.cheongmaru.domain.place.dto;

import com.cheongmaru.domain.place.domain.Place;
import com.cheongmaru.domain.tag.domain.Tag; // <-- 추가된 import 문
import java.util.List; // <-- 추가된 import 문
import java.util.stream.Collectors; // <-- 추가된 import 문

public class PlaceDto {
    private Long id;
    private String name;
    private String address;
    private int capacity;
    private String description;
    private double latitude;
    private double longitude;
    private String link;
    private List<Long> tagIds; // <-- 새로 추가된 필드

    public PlaceDto(Long id, String name, String address,
                    int capacity, String description, double latitude, double longitude, String link, List<Long> tagIds) { // <-- 생성자 파라미터에 tagIds 추가
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.link = link;
        this.tagIds = tagIds; // <-- 생성자 내부 초기화 추가
    }

    public static PlaceDto fromEntity(Place place) {
        return new PlaceDto(
                place.getId(),
                place.getName(),
                place.getAddress(),
                place.getCapacity() != null ? place.getCapacity() : 0,
                place.getDescription(),
                place.getLatitude(),
                place.getLongitude(),
                place.getLink(),
                place.getTags().stream().map(Tag::getId).collect(Collectors.toList()) // <-- tagIds 값을 가져와서 설정하는 로직 추가
        );
    }

    // Getter들 (link getter는 기존에 있었고, 아래 tagIds getter가 새로 추가됨)
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getCapacity() { return capacity; }
    public String getDescription() { return description; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getLink() { return link; }
    public List<Long> getTagIds() { return tagIds; } // <-- 새로 추가된 getter
}
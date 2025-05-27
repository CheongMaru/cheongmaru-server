package com.cheongmaru.domain.place.dto;

import com.cheongmaru.domain.place.domain.Place;

public class PlaceDto {
    private Long id;
    private String name;
    private String address;

    // ✅ 추가된 필드들
    private int capacity;
    private String description;
    private double latitude;
    private double longitude;

    // 생성자
    public PlaceDto(Long id, String name, String address,
                    int capacity, String description, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // ✅ Entity → DTO 변환 메서드
    public static PlaceDto fromEntity(Place place) {
        return new PlaceDto(
                place.getId(),
                place.getName(),
                place.getAddress(),
                place.getCapacity() != null ? place.getCapacity() : 0,  // ← null 방어 처리
                place.getDescription(),
                place.getLatitude(),
                place.getLongitude()
        );
    }


    // Getter들
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getCapacity() { return capacity; }
    public String getDescription() { return description; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}

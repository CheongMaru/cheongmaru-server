package com.cheongmaru.domain.place.dto;

import com.cheongmaru.domain.place.domain.Place;

public class PlaceDto {
    private Long id;
    private String name;
    private String address;
    private int capacity;
    private String description;
    private double latitude;
    private double longitude;
    private String link; // <-- 이 부분을 추가합니다.

    public PlaceDto(Long id, String name, String address,
                    int capacity, String description, double latitude, double longitude, String link) { // <-- 생성자에도 link 추가
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.link = link; // <-- 이 부분을 추가합니다.
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
                place.getLink() // <-- link 정보를 추가합니다.
        );
    }

    // Getter들 (link getter 추가)
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getCapacity() { return capacity; }
    public String getDescription() { return description; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getLink() { return link; } // <-- link getter 추가
}
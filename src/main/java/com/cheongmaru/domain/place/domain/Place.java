package com.cheongmaru.domain.place.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "places")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @Column(nullable = true)
    private Integer capacity;        // 수용 인원수 (null 허용)

    @Column(length = 2000)
    private String description;      // 공간 설명 (길이 늘림)

    private double latitude;         // 위도
    private double longitude;        // 경도

    // 기본 생성자 (JPA용)
    protected Place() {}

    // 편의 생성자 (전체 필드)
    public Place(String name, String address, Integer capacity, String description, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getter들
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public Integer getCapacity() { return capacity; }
    public String getDescription() { return description; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}

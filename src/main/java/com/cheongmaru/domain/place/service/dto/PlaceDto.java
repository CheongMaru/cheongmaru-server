package com.cheongmaru.domain.place.service.dto;

public class PlaceDto {
    private Long id;
    private String name;
    private String address;

    public PlaceDto(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
}

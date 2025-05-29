package com.cheongmaru.domain.place.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.cheongmaru.domain.tag.domain.Tag;

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
    private String description;      // 공간 설명

    private double latitude;         // 위도
    private double longitude;        // 경도

    @ManyToMany
    @JoinTable(
            name = "place_tag",
            joinColumns = @JoinColumn(name = "place_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    protected Place() {}

    public Place(String name, String address, Integer capacity, String description, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public Integer getCapacity() { return capacity; }
    public String getDescription() { return description; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public List<Tag> getTags() { return tags; }
}

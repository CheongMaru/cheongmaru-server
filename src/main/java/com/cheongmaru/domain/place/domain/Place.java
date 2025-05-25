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

    // 기본 생성자 (JPA용)
    protected Place() {}

    // 편의 생성자
    public Place(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getter만 있어도 읽기만 가능
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
}

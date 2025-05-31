package com.cheongmaru.domain.location.dto;

import lombok.Data;
import java.util.List;

@Data
public class KakaoGeoResponse {
    private List<Document> documents;

    @Data
    public static class Document {
        private Address address;
    }

    @Data
    public static class Address {
        private String region_1depth_name;
        private String region_2depth_name;
        private String region_3depth_name;
        private String address_name;
    }
}

package com.cheongmaru.domain.location.service;

import com.cheongmaru.domain.location.domain.Location;
import com.cheongmaru.domain.location.dto.LocationRequest;
import com.cheongmaru.domain.location.repository.LocationRepository;
import com.cheongmaru.domain.user.domain.User;
import com.cheongmaru.domain.user.repository.UserRepository;
import com.cheongmaru.global.exception.CustomException;
import com.cheongmaru.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LocationService {

    @Value("${KAKAO_REST_API_KEY}")
    private String kakaoRestApiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://dapi.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    /**
     * 위치 인증 여부 확인
     */
    public boolean isLocationCertified(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return locationRepository.findByUser(user).isPresent();
    }

    /**
     * 위치 인증 처리
     */

    public boolean certify(String email, LocationRequest request) {
        String uri = String.format("/v2/local/geo/coord2address.json?x=%f&y=%f",
                request.longitude(), request.latitude());

        Map<String, Object> response = webClient.get()
                .uri(uri)
                .header("Authorization", "KakaoAK " + kakaoRestApiKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null || response.get("documents") == null) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        List<?> documents = (List<?>) response.get("documents");
        if (documents.isEmpty()) {
            return false;
        }

        Map<?, ?> document = (Map<?, ?>) documents.get(0);
        Map<?, ?> address = (Map<?, ?>) document.get("address");

        if (address == null) {
            return false;
        }

        String region = (String) address.get("region_2depth_name"); // ex) 흥덕구, 상당구

        if (region != null && region.contains("청주")) {
            // ✅ 사용자 정보 조회
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            // ✅ 기존 인증이력 확인
            Location location = locationRepository.findByUser(user)
                    .map(existing -> {
                        existing.update(request.latitude(), request.longitude());
                        return existing;
                    })
                    .orElseGet(() -> Location.create(user, request.latitude(), request.longitude()));

            // ✅ 저장 또는 갱신
            locationRepository.save(location);

            return true;
        }

        return false;
    }

}

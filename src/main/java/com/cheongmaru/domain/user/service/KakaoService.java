package com.cheongmaru.domain.user.service;

import com.cheongmaru.domain.user.dto.AccessTokenDto;
import com.cheongmaru.domain.user.dto.KakaoProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${oauth.kakao.client-id}")
    private String kakaoClientId;

    @Value("${oauth.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${oauth.kakao.token-uri}")
    private String kakaoTokenUri;

    @Value("${oauth.kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    private final RestTemplate restTemplate = new RestTemplate();

   //인가 코드를 이용해 카카오 access token 요청
    public AccessTokenDto getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<AccessTokenDto> response = restTemplate.postForEntity(
                kakaoTokenUri,
                request,
                AccessTokenDto.class
        );

        return response.getBody();
    }

    //access token을 이용해 카카오 사용자 정보 요청
    public KakaoProfileDto getKakaoProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoProfileDto> response = restTemplate.exchange(
                kakaoUserInfoUri,
                HttpMethod.GET,
                request,
                KakaoProfileDto.class
        );

        return response.getBody();
    }
}

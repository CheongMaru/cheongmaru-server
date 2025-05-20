package com.cheongmaru.user.service;

import com.cheongmaru.user.domain.User;
import com.cheongmaru.user.dto.KakaoProfileDto;
import com.cheongmaru.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findOrCreateUser(KakaoProfileDto profile) {
        return userRepository.findByKakaoId(profile.getKakaoId())
                .orElseGet(() -> userRepository.save(
                        User.createFromKakao(
                                profile.getKakaoId(),
                                profile.getNickname(),
                                profile.getEmail(),
                                profile.getAgeRange(),
                                profile.getGender()
                        )
                ));
    }
}

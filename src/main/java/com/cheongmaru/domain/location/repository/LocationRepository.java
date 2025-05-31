package com.cheongmaru.domain.location.repository;

import com.cheongmaru.domain.location.domain.Location;
import com.cheongmaru.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByUser(User user);
}

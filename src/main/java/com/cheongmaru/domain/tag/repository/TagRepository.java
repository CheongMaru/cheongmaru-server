package com.cheongmaru.domain.tag.repository;

import com.cheongmaru.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional; // Optional import 추가

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name); // 추가
}
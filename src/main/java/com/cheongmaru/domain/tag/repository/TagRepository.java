package com.cheongmaru.domain.tag.repository;

import com.cheongmaru.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}

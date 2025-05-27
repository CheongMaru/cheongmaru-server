package com.cheongmaru.domain.post.repository;

import com.cheongmaru.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

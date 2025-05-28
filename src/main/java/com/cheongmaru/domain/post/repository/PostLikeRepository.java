package com.cheongmaru.domain.post.repository;

import com.cheongmaru.domain.post.domain.Post;
import com.cheongmaru.domain.post.domain.PostLike;
import com.cheongmaru.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    void deleteByUserAndPost(User user, Post post);
    List<PostLike> findAllByUserOrderByPostCreatedAtDesc(User user);

}

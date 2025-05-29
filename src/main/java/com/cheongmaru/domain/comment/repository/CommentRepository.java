package com.cheongmaru.domain.comment.repository;

import com.cheongmaru.domain.comment.domain.Comment;
import com.cheongmaru.domain.post.domain.Post;
import com.cheongmaru.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreatedAtDesc(Post post);
    List<Comment> findByUserOrderByCreatedAtDesc(User user);
}

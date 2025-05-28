package com.cheongmaru.domain.post.service;

import com.cheongmaru.domain.post.domain.Post;
import com.cheongmaru.domain.post.domain.PostLike;
import com.cheongmaru.domain.post.dto.PostResponse;
import com.cheongmaru.domain.post.repository.PostLikeRepository;
import com.cheongmaru.domain.post.repository.PostRepository;
import com.cheongmaru.domain.user.domain.User;
import com.cheongmaru.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    /**
     * 게시글 좋아요
     */
    @Transactional
    public void likePost(String email, Long postId) {
        User user = getUserByEmail(email);
        Post post = getPostById(postId);

        boolean alreadyLiked = postLikeRepository.findByUserAndPost(user, post).isPresent();
        if (!alreadyLiked) {
            postLikeRepository.save(PostLike.create(user, post));
            post.increaseLikeCount();
        }
    }

    /**
     * 게시글 좋아요 취소
     */
    @Transactional
    public void unlikePost(String email, Long postId) {
        User user = getUserByEmail(email);
        Post post = getPostById(postId);

        postLikeRepository.findByUserAndPost(user, post)
                .ifPresent(like -> {
                    postLikeRepository.delete(like);
                    post.decreaseLikeCount();
                });
    }

    /**
     * 내가 좋아요한 게시글 조회
     */
    @Transactional
    public List<PostResponse> getLikedPosts(String email) {
        User user = getUserByEmail(email);

        return postLikeRepository.findAllByUserOrderByPostCreatedAtDesc(user).stream()
                .map(PostLike::getPost)
                .map(PostResponse::from)
                .toList();
    }
}

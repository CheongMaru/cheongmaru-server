package com.cheongmaru.domain.post.service;

import com.cheongmaru.domain.category.domain.Category;
import com.cheongmaru.domain.category.repository.CategoryRepository;
import com.cheongmaru.domain.post.domain.Post;
import com.cheongmaru.domain.post.dto.PostRequest;
import com.cheongmaru.domain.post.repository.PostRepository;
import com.cheongmaru.domain.user.domain.User;
import com.cheongmaru.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createPost(String email, PostRequest request) {
        User user = getUserByEmail(email);
        Category category = getCategoryById(request.getCategoryId());

        Post post = Post.create(user, category, request.getTitle(), request.getContent());

        if (request.getImageUrls() != null) {
            request.getImageUrls().forEach(post::addImage);
        }

        return postRepository.save(post).getId();
    }


    /**
     * 게시글 수정 후 메시지 반환
     */
    @Transactional
    public String updatePost(Long postId, String email, PostRequest request) {
        User user = getUserByEmail(email);
        Post post = getPostById(postId);

        if (!post.getUser().equals(user)) {
            throw new SecurityException("해당 게시글에 대한 수정 권한이 없습니다.");
        }

        Category category = getCategoryById(request.getCategoryId());

        post.update(category, request.getTitle(), request.getContent());

        if (request.getImageUrls() != null) {
            post.replaceImages(request.getImageUrls());
        }

        return "게시글 수정이 완료되었습니다.";
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }


    /**
     * 게시글 삭제
     */
    @Transactional
    public String deletePost(Long postId, String email) {
        User user = getUserByEmail(email);
        Post post = getPostById(postId);

        // 작성자 확인
        if (!post.getUser().equals(user)) {
            throw new SecurityException("해당 게시글에 대한 삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
        return "게시글이 성공적으로 삭제되었습니다.";
    }

}

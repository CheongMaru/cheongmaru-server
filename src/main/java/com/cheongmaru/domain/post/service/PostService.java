package com.cheongmaru.domain.post.service;

import com.cheongmaru.domain.category.domain.Category;
import com.cheongmaru.domain.category.repository.CategoryRepository;
import com.cheongmaru.domain.post.domain.Post;
import com.cheongmaru.domain.post.dto.PostDetailResponse;
import com.cheongmaru.domain.post.dto.PostRequest;
import com.cheongmaru.domain.post.dto.PostResponse;
import com.cheongmaru.domain.post.repository.PostRepository;
import com.cheongmaru.domain.user.domain.User;
import com.cheongmaru.domain.user.repository.UserRepository;
import com.cheongmaru.global.exception.CustomException;
import com.cheongmaru.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    /**
     * 게시글 작성
     */
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
     * 게시글 수정
     */
    @Transactional
    public String updatePost(Long postId, String email, PostRequest request) {
        User user = getUserByEmail(email);
        Post post = getPostById(postId);

        if (!post.getUser().equals(user)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        Category category = getCategoryById(request.getCategoryId());
        post.update(category, request.getTitle(), request.getContent());

        if (request.getImageUrls() != null) {
            post.replaceImages(request.getImageUrls());
        }

        return "게시글 수정이 완료되었습니다.";
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public String deletePost(Long postId, String email) {
        User user = getUserByEmail(email);
        Post post = getPostById(postId);

        if (!post.getUser().equals(user)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        postRepository.delete(post);
        return "게시글이 성공적으로 삭제되었습니다.";
    }

    /**
     * 전체 게시글 목록 조회
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    /**
     * 카테고리별 게시글 목록 조회
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PostResponse> getPostsByCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);

        return postRepository.findByCategoryOrderByCreatedAtDesc(category)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    /**
     * 게시글 검색 (제목 or 내용)
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PostResponse> searchPosts(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }

        return postRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDesc(query, query)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    /**
     * 게시글 상세 조회
     */
    @Transactional
    public PostDetailResponse getPostDetail(Long postId) {
        Post post = getPostById(postId);
        post.increaseViewCount();

        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getUser().getNickname())
                .category(post.getCategory().getName())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .imageUrls(post.getImageUrls())
                .createdAt(post.getCreatedAt())
                .build();
    }
}

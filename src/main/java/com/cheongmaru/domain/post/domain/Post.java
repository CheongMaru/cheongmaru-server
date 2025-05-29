package com.cheongmaru.domain.post.domain;

import com.cheongmaru.domain.category.domain.Category;
import com.cheongmaru.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "comment_count")
    private int commentCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PostImage> images = new ArrayList<>();

    protected Post() {}

    private Post(User user, Category category, String title, String content) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
        this.viewCount = 0;
        this.likeCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public static Post create(User user, Category category, String title, String content) {
        return new Post(user, category, title, content);
    }

    // 이미지 추가 (PostImage 직접 생성하지 않도록 캡슐화)
    public void addImage(String imgUrl) {
        this.images.add(PostImage.create(this, imgUrl));
    }

    // 전체 이미지 교체 (예: 수정 시 기존 이미지 모두 제거하고 새로 추가)
    public void replaceImages(List<String> newImageUrls) {
        this.images.clear();
        newImageUrls.forEach(url -> this.addImage(url));
    }

    public void update(Category category, String title, String content) {
        this.category = category;
        this.title = title;
        this.content = content;
    }

    // 조회수 증가
    public void increaseViewCount() {
        this.viewCount += 1;
    }

    // 좋아요 수 증가
    public void increaseLikeCount() {
        this.likeCount += 1;
    }

    // 좋아요 수 감소
    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount -= 1;
        }
    }

    // 댓글 수 증가
    public void increaseCommentCount() {
        this.commentCount++;
    }

    // 댓글 수 감소
    public void decreaseCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }

    // 이미지 URL 리스트 반환
    public List<String> getImageUrls() {
        return images.stream()
                .map(PostImage::getImageUrl)
                .toList();
    }


}


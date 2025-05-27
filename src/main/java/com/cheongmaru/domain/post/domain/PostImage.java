package com.cheongmaru.domain.post.domain;

import jakarta.persistence.*;
@Entity
@Table(name = "post_image")
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "img_url", nullable = false, columnDefinition = "TEXT")
    private String imgUrl;

    protected PostImage() {}

    private PostImage(Post post, String imgUrl) {
        this.post = post;
        this.imgUrl = imgUrl;
    }

    public static PostImage create(Post post, String imgUrl) {
        return new PostImage(post, imgUrl);
    }
}

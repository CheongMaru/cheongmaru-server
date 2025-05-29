package com.cheongmaru.domain.comment.service;

import com.cheongmaru.domain.comment.domain.Comment;
import com.cheongmaru.domain.comment.dto.CommentRequest;
import com.cheongmaru.domain.comment.dto.CommentResponse;
import com.cheongmaru.domain.comment.repository.CommentRepository;
import com.cheongmaru.domain.post.domain.Post;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    /**
     * 댓글 작성
     */
    @Transactional
    public Long createComment(String email, Long postId, CommentRequest request) {
        User user = getUserByEmail(email);
        Post post = getPostById(postId);

        Comment comment = Comment.create(post, user, request.getContent());
        commentRepository.save(comment);

        post.increaseCommentCount();
        return comment.getId();
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public void updateComment(String email, Long commentId, CommentRequest request) {
        User user = getUserByEmail(email);
        Comment comment = getCommentById(commentId);

        if (!comment.getUser().equals(user)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        comment.update(request.getContent());
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteComment(String email, Long commentId) {
        User user = getUserByEmail(email);
        Comment comment = getCommentById(commentId);

        if (!comment.getUser().equals(user)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        comment.getPost().decreaseCommentCount();
        commentRepository.delete(comment);
    }

    /**
     * 게시글의 댓글 목록 조회
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<CommentResponse> getCommentsByPost(Long postId) {
        Post post = getPostById(postId);
        return commentRepository.findByPostOrderByCreatedAtDesc(post)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    /**
     * 내 댓글 목록 조회
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<CommentResponse> getCommentsByUser(String email) {
        User user = getUserByEmail(email);
        return commentRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}

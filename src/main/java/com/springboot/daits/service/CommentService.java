package com.springboot.daits.service;

import com.springboot.daits.model.CommentInput;
import com.springboot.daits.response.CommentResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {

    ResponseEntity<?> createComment(Long post_id, CommentInput commentInput);

    CommentResponse getComment(Long post_id, Long comment_id);

    List<CommentResponse> getComments(Long post_id);

    ResponseEntity<?> updateComment(Long post_id, Long comment_id, CommentInput commentInput);

    ResponseEntity<?> deleteComment(Long post_id, Long comment_id);

    ResponseEntity<?> recommendComment(Long post_id, Long comment_id);

    ResponseEntity<?> notRecommendComment(Long post_id, Long comment_id);

}

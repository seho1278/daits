package com.springboot.daits.controller;

import com.springboot.daits.model.CommentInput;
import com.springboot.daits.response.CommentResponse;
import com.springboot.daits.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    // 게시글 생성
    @PostMapping("/create/{post_id}")
    public ResponseEntity<?> createComment(@PathVariable Long post_id, @RequestBody CommentInput commentInput) {
        return commentService.createComment(post_id, commentInput);
    }
    
    // 단일 댓글 조회
    @GetMapping("/{post_id}/{comment_id}")
    public CommentResponse getComment(@PathVariable Long post_id, @PathVariable Long comment_id) {
        return commentService.getComment(post_id, comment_id);
    }

    // 모든 댓글 조회
    @GetMapping("/{post_id}")
    public List<CommentResponse> getComments(@PathVariable Long post_id) {
        return commentService.getComments(post_id);
    }

    // 댓글 수정
    @PatchMapping("/update/{post_id}/{comment_id}")
    public ResponseEntity<?> updateComment(@PathVariable Long post_id, @PathVariable Long comment_id, @RequestBody CommentInput commentInput) {
        return commentService.updateComment(post_id, comment_id, commentInput);
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{post_id}/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id) {
        return commentService.deleteComment(post_id, comment_id);
    }

    // 댓글 추천
    @PostMapping("/recommend/{post_id}/{comment_id}")
    public ResponseEntity<?> recommendComment(@PathVariable Long post_id, @PathVariable Long comment_id) {
        return commentService.recommendComment(post_id, comment_id);
    }

    // 댓글 비추천
    @PostMapping("/notrecommend/{post_id}/{comment_id}")
    public ResponseEntity<?> notRecommendComment(@PathVariable Long post_id, @PathVariable Long comment_id) {
        return commentService.notRecommendComment(post_id, comment_id);
    }



}

package com.springboot.daits.notice.controller;

import com.springboot.daits.notice.entity.Post;
import com.springboot.daits.notice.exception.PostNotFoundException;
import com.springboot.daits.notice.model.PostInput;
import com.springboot.daits.notice.model.PostResponse;
import com.springboot.daits.notice.model.ResponseError;
import com.springboot.daits.notice.repository.PostRepository;
import com.springboot.daits.notice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    // 게시글 생성
    @PostMapping("/api/post")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostInput postInput, Errors errors) {

        return postService.createPost(postInput, errors);
    }

    // 단일 게시글 조회
    @GetMapping("/api/post/{id}")
    public PostResponse getPost(@PathVariable Long id) {

        return postService.getPost(id);
    }

    // 단일 게시글 수정
    @PutMapping("/api/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody @Valid PostInput postInput) {

        return postService.updatePost(id, postInput);
    }

    // 단일 게시글 삭제
    @DeleteMapping("/api/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        return postService.deletePost(id);
    }

}

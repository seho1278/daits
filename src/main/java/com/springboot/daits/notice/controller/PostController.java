package com.springboot.daits.notice.controller;

import com.springboot.daits.notice.model.PostInput;
import com.springboot.daits.notice.model.PostResponse;
import com.springboot.daits.notice.repository.PostRepository;
import com.springboot.daits.notice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    // 게시글 생성
    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostInput postInput, Errors errors) {

        return postService.createPost(postInput, errors);
    }

    // 단일 게시글 조회
    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {

        return postService.getPost(id);
    }

    // 단일 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody @Valid PostInput postInput) {

        return postService.updatePost(id, postInput);
    }

    // 단일 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        return postService.deletePost(id);
    }

}

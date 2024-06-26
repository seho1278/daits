package com.springboot.daits.notice.controller;

import com.springboot.daits.notice.entity.Post;
import com.springboot.daits.notice.exception.PostNotFoundException;
import com.springboot.daits.notice.model.PostInput;
import com.springboot.daits.notice.model.PostResponse;
import com.springboot.daits.notice.model.ResponseError;
import com.springboot.daits.notice.repository.PostRepository;
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

    private final PostRepository noticeRepository;
    private final PostRepository postRepository;

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<?> NotFoundPostExceptionHandler(PostNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    
    // 게시글 생성
    @PostMapping("/api/post")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostInput postInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError)e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        Post post = Post.builder()
                .category(postInput.getCategory())
                .title(postInput.getTitle())
                .contents(postInput.getContents())
                .view(0)
                .recommendation(0)
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.save(post);

        return ResponseEntity.ok().build();
    }

    // 단일 게시글 조회
    @GetMapping("/api/post/{id}")
    public PostResponse getPost(@PathVariable Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("게시글이 존재하지 않습니다."));

        PostResponse postResponse = PostResponse.of(post);

        return postResponse;
    }

    // 단일 게시글 수정
    @PutMapping("/api/post/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody @Valid PostInput postInput, Errors errors) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("게시글이 존재하지 않습니다."));

        post.setCategory(postInput.getCategory());
        post.setTitle(postInput.getTitle());
        post.setContents(postInput.getContents());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);
    }

    // 단일 게시글 삭제
    @DeleteMapping("/api/post/{id}")
    public void deletePost(@PathVariable Long id) {

        Post post = postRepository.findById(id)
                        .orElseThrow(() -> new PostNotFoundException("게시글이 존재하지 않습ㄴ디ㅏ."));

        postRepository.delete(post);
    }

}

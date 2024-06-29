package com.springboot.daits.notice.service;

import com.springboot.daits.notice.entity.Post;
import com.springboot.daits.notice.exception.PostNotFoundException;
import com.springboot.daits.notice.model.PostInput;
import com.springboot.daits.notice.model.PostResponse;
import com.springboot.daits.notice.model.ResponseError;
import com.springboot.daits.notice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 확인
    public Post checkPost(Long id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("게시글이 존재하지 않습니다."));
    }

    // 게시글 생성
    public ResponseEntity<?> createPost(PostInput postInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
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

    // 게시글 조회
    public PostResponse getPost(Long id) {
        Post post = checkPost(id);

        return PostResponse.of(post);
    }

    // 게시글 수정
    public ResponseEntity<?> updatePost(Long id, PostInput postInput) {

        Post post = checkPost(id);

        post.setCategory(postInput.getCategory());
        post.setTitle(postInput.getTitle());
        post.setContents(postInput.getContents());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    public ResponseEntity<?> deletePost(Long id) {

        Post post = checkPost(id);

        postRepository.delete(post);

        return ResponseEntity.ok().build();
    }

}

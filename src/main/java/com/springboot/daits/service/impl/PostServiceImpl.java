package com.springboot.daits.service.impl;

import com.springboot.daits.entity.Member;
import com.springboot.daits.entity.Post;
import com.springboot.daits.entity.specification.PostSpecifications;
import com.springboot.daits.exception.PostNotFoundException;
import com.springboot.daits.exception.UserNotFoundException;
import com.springboot.daits.exception.UserNotMatchException;
import com.springboot.daits.model.PostInput;
import com.springboot.daits.response.PostResponse;
import com.springboot.daits.service.PostService;
import com.springboot.daits.response.ResponseError;
import com.springboot.daits.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    // 인증된 사용자 Authentication 객체 가져오기
    public Member getMemberToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        return member;
    }

    // 게시글 확인
    public Post checkPost(Long id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("게시글이 존재하지 않습니다."));
    }

    // 게시글 생성
    @Override
    public ResponseEntity<?> createPost(PostInput postInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        // 현재 인증된 사용자 가져오기
        Member member = getMemberToken();

        Post post = Post.builder()
                .category(postInput.getCategory())
                .title(postInput.getTitle())
                .contents(postInput.getContents())
                .view(0)
                .recommendation(0)
                .createdAt(LocalDateTime.now())
                .member(member)
                .build();

        postRepository.save(post);

        return ResponseEntity.ok().build();
    }

    // 게시글 조회
    @Override
    public PostResponse getPost(Long id) {
        Post post = checkPost(id);

        // 게시글 조회 시 view
        post.setView(post.getView() + 1);
        postRepository.save(post);

        return PostResponse.of(post);
    }

    // 게시글 수정
    @Override
    public ResponseEntity<?> updatePost(Long id, PostInput postInput) {

        Post post = checkPost(id);
        Member member = getMemberToken();

        if (!member.getRoles().contains("ROLE_ADMIN")) {
            if (!post.getMember().getEmail().equals(member.getEmail())) {
                throw new UserNotMatchException("작성자 본인만 수정 가능합니다.");
            }
        }

        post.setCategory(postInput.getCategory());
        post.setTitle(postInput.getTitle());
        post.setContents(postInput.getContents());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    @Override
    public ResponseEntity<?> deletePost(Long id) {

        Post post = checkPost(id);
        Member member = getMemberToken();

        if (!member.getRoles().contains("ROLE_ADMIN")) {
            if (!post.getMember().getEmail().equals(member.getEmail())) {
                throw new UserNotMatchException("작성자 본인만 삭제가 가능합니다.");
            }
        }

        postRepository.delete(post);

        return ResponseEntity.ok().build();
    }

    // 게시글 추천
    @Override
    public ResponseEntity<?> recommendPost(Long id) {
        // 게시글 확인
        Post post = checkPost(id);

        // 본인 확인
        Member member = getMemberToken();
        if (member == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }

        // 추천수 증가
        post.setRecommendation(post.getRecommendation() + 1);

        postRepository.save(post);

        return ResponseEntity.ok().build();
    }

    // 게시글 비추천
    @Override
    public ResponseEntity<?> notRecommendPost(Long id) {
        // 게시글 확인
        Post post = checkPost(id);

        // 본인확인
        Member member = getMemberToken();
        if (member == null) {
            throw new UserNotFoundException("로그인이 필요합니다");
        }

        // 추천수 감소
        post.setRecommendation(post.getRecommendation() - 1);

        postRepository.save(post);

        return ResponseEntity.ok().build();
    }

    // 특정 게시글 검색
    @Override
    public List<PostResponse> searchPost(String keyword, String type) {
        Specification<Post> spec = null;

        switch (type) {
            case "title":
                spec = PostSpecifications.hasTitle(keyword);
                break;
            case "contents":
                spec = PostSpecifications.hasContents(keyword);
            case "username":
                spec = PostSpecifications.hasUserName(keyword);
            case "titleOrContents":
                spec = PostSpecifications.hasTitleOrContents(keyword);
        }

        List<Post> posts = postRepository.findAll(spec);

        return posts.stream().map(PostResponse::of).collect(Collectors.toList());
    }
}

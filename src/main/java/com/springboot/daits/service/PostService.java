package com.springboot.daits.service;

import com.springboot.daits.model.PostInput;
import com.springboot.daits.response.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.List;

public interface PostService {

    ResponseEntity<?> createPost(PostInput postInput, Errors errors);

    PostResponse getPost(Long id);

    ResponseEntity<?> updatePost(Long id, PostInput postInput);

    ResponseEntity<?> deletePost(Long id);

    ResponseEntity<?> recommendPost(Long id);

    ResponseEntity<?> notRecommendPost(Long id);

    List<PostResponse> searchPost(String keyword, String type);
}

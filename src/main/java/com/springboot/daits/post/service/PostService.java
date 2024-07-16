package com.springboot.daits.post.service;

import com.springboot.daits.post.model.PostInput;
import com.springboot.daits.post.model.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public interface PostService {

    ResponseEntity<?> createPost(PostInput postInput, Errors errors);

    PostResponse getPost(Long id);

    ResponseEntity<?> updatePost(Long id, PostInput postInput);

    ResponseEntity<?> deletePost(Long id);

}

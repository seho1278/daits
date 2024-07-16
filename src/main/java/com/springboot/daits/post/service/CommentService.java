package com.springboot.daits.post.service;

import com.springboot.daits.post.model.CommentInput;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    ResponseEntity<?> createComment(Long id, CommentInput commentInput);

//    CommentResponse getComment(Long id)

}

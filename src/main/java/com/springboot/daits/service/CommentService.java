package com.springboot.daits.service;

import com.springboot.daits.model.CommentInput;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    ResponseEntity<?> createComment(Long id, CommentInput commentInput);

//    CommentResponse getComment(Long id)

}

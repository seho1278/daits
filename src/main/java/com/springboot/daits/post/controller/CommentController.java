package com.springboot.daits.post.controller;

import com.springboot.daits.post.model.CommentInput;
import com.springboot.daits.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}/create")
    public ResponseEntity<?> createComment(@PathVariable Long id, @RequestBody CommentInput commentInput) {
        return commentService.createComment(id, commentInput);
    }


}

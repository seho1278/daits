package com.springboot.daits.Comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.daits.entity.Comment;
import com.springboot.daits.entity.Post;
import com.springboot.daits.model.CommentInput;
import com.springboot.daits.repository.CommentRepository;
import com.springboot.daits.repository.PostRepository;
import com.springboot.daits.response.CommentResponse;
import com.springboot.daits.service.CommentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@SpringBootTest
public class CommentCreateTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Test
    @Transactional
    public void  testCommentCreate() throws Exception {
        Post post = new Post();
        post.setCategory("게임");
        post.setTitle("제목1");
        post.setContents("내용1");

        post = postRepository.save(post);

        Comment comment = new Comment();
        comment.setContents("test Create");

        String commentInputJson = jacksonObjectMapper.writeValueAsString(comment);

    }

}

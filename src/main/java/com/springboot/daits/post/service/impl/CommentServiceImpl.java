package com.springboot.daits.post.service.impl;

import com.springboot.daits.Member.entity.Member;
import com.springboot.daits.post.entity.Comment;
import com.springboot.daits.post.entity.Post;
import com.springboot.daits.post.exception.NotFoundMemberToken;
import com.springboot.daits.post.exception.PostNotFoundException;
import com.springboot.daits.post.model.CommentInput;
import com.springboot.daits.post.repository.CommentRepository;
import com.springboot.daits.post.repository.PostRepository;
import com.springboot.daits.post.service.CommentService;
import com.springboot.daits.security.GetMemberToken;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 인증된 사용자 Authentication 객체 가져오기
    public Member getMemberToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        return member;
    }


    //게시글 체크
    public Post checkPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("게시글이 존재하지 않습니다."));
    }

    @Override
    public ResponseEntity<?> createComment(Long id, CommentInput commentInput) {

        // 토큰 가져오기
        Member member = getMemberToken();

        System.out.println(member.getEmail());
        
        //게시글이 있는지 확인
        Post post = checkPost(id);

        Comment comment = Comment.builder()
                .contents(commentInput.getContents())
                .recommendation(0)
                .createdAt(LocalDateTime.now())
                .post(post)
                .member(member)
                .build();

        commentRepository.save(comment);

        return ResponseEntity.ok().build();
    }
}

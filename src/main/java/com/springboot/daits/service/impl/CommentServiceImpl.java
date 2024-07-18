package com.springboot.daits.service.impl;

import com.springboot.daits.entity.Member;
import com.springboot.daits.entity.Comment;
import com.springboot.daits.entity.Post;
import com.springboot.daits.exception.CommentNotFoundException;
import com.springboot.daits.exception.PostNotFoundException;
import com.springboot.daits.exception.UserNotMatchException;
import com.springboot.daits.model.CommentInput;
import com.springboot.daits.repository.CommentRepository;
import com.springboot.daits.repository.PostRepository;
import com.springboot.daits.response.CommentResponse;
import com.springboot.daits.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    // 댓글 작성
    @Override
    public ResponseEntity<?> createComment(Long post_id, CommentInput commentInput) {

        // 토큰 가져오기
        Member member = getMemberToken();

        System.out.println(member.getEmail());
        
        //게시글이 있는지 확인
        Post post = checkPost(post_id);

        Comment comment = Comment.builder()
                .contents(commentInput.getContents())
                .recommendation(0)
                .createdAt(LocalDateTime.now())
                .post(post)
                .member(member)
                .build();

        post.getComments().add(comment);

        commentRepository.save(comment);

        return ResponseEntity.ok().build();
    }
    
    // 단일 댓글 조회
    @Override
    public CommentResponse getComment(Long post_id, Long comment_id) {
        // 게시글 체크
        Post post = checkPost(post_id);

        // 댓글 체크
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));

        return CommentResponse.of(comment);
    }

    // 게시글에 달린 모든 댓글 조회
    @Override
    public List<CommentResponse> getComments(Long post_id) {
        // 게시글 확인
        Post post = checkPost(post_id);

        // 댓글 가져오기
        List<Comment> comments = post.getComments();
        if (comments.isEmpty()) {
            throw new CommentNotFoundException("댓글이 존재하지 않습니다.");
        }
//        List<Comment> comments = commentRepository.findAllByPostId(post_id)
//                .orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다."));

        return comments.stream().map(CommentResponse::of).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> updateComment(Long post_id, Long comment_id, CommentInput commentInput) {

        // 게시글 확인
        Post post = checkPost(post_id);

        // 댓글 확인
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다."));

        // 본인확인
        Member member = getMemberToken();
        if (member == null || !comment.getMember().getEmail().equals(member.getEmail())) {
            throw new UserNotMatchException("작성자 본인만 수정이 가능합니다.");
        }

        comment.setContents(commentInput.getContents());
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteComment(Long post_id, Long comment_id) {

        // 게시글확인
        Post post = checkPost(post_id);

        // 댓글 확인
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다."));

        // 본인확인
        Member member = getMemberToken();

        if (member == null || !comment.getMember().getEmail().equals(member.getEmail())) {
            throw new UserNotMatchException("작성자 본인만 삭제가 가능합니다.");
        }


        // 삭제
        commentRepository.delete(comment);

        return ResponseEntity.ok().build();
    }
}

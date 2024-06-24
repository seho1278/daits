package com.springboot.daits.notice.model;

import com.springboot.daits.notice.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private Long id;
    private String category;
    private String title;
    private String contents;
    private int view;
    private int recommendation;
    private LocalDateTime createdAt;


    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .category(post.getCategory())
                .title(post.getTitle())
                .contents(post.getContents())
                .view(post.getView())
                .recommendation(post.getRecommendation())
                .createdAt(post.getCreatedAt())
                .build();
    }
}

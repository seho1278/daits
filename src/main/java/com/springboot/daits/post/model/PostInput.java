package com.springboot.daits.post.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostInput {

    @NotBlank(message = "카테고리는 필수 선택사항입니다.")
    private String category;

    @NotBlank(message = "제목은 필수 입력사항입니다.")
    private String title;

    private String contents;

}

package com.springboot.daits.entity.specification;

import com.springboot.daits.entity.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecifications {

    public static Specification<Post> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Post> hasContents(String contents) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("contents")), "%" + contents.toLowerCase() + "%");
    }

    public static Specification<Post> hasUserName(String member) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.join("member").get("userName")), "%" + member.toLowerCase() + "%");
    }

    public static Specification<Post> hasTitleOrContents(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("contents")), "%" + keyword.toLowerCase() + "%")
        );
    }
}

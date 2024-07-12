package com.springboot.daits.Member.repository;

import com.springboot.daits.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findById(Long id);

    Optional<Member> findByEmailAndPassword(String email, String password);

    Member findByPassword(String password);

//    Member getByUid(String uid);
}

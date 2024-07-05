package com.springboot.daits.User.repository;

import com.springboot.daits.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    Optional<User> findByEmailAndPassword(String email, String password);

    User findByPassword(String password);
}

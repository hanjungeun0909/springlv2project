package com.sparta.springlv2project.repository;

import com.sparta.springlv2project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
   List<Post> findAllByUsernameOrderByCreatedAtDesc(String username);
   List<Post> findAllByOrderByCreatedAtDesc();
}

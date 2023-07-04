package com.sparta.springlv2project.repository;

import com.sparta.springlv2project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


public interface BoardRepository extends JpaRepository<Post, Long> {
}

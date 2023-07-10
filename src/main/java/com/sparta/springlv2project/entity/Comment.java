package com.sparta.springlv2project.entity;

import com.sparta.springlv2project.dto.boardDto.CommentRequestDto;
import io.jsonwebtoken.Claims;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(name="username", nullable = false)
    private String username;
    @Column(name="comment", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    public Comment(CommentRequestDto requestDto, Claims userInfo, Post post){
        this.username = userInfo.getSubject();
        this.comment = requestDto.getComment();
        this.post = post;
    }
}

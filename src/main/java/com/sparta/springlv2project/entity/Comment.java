package com.sparta.springlv2project.entity;

import com.sparta.springlv2project.dto.boardDto.CommentRequestDto;
import com.sparta.springlv2project.dto.boardDto.PostRequestDto;
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

    public Boolean verifyAuthority(Claims userInfo, String username) {
        UserRoleEnum role= (UserRoleEnum)userInfo.get("AUTHORIZATION_KEY");
        String tokenName = userInfo.getSubject();
        if (!((role!=null && role.equals(UserRoleEnum.ADMIN)) || tokenName.equals(username))) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
        return true;
    }

    public void update(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
    }
}

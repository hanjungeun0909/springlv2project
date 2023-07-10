package com.sparta.springlv2project.dto.boardDto;

import com.sparta.springlv2project.entity.Comment;
import com.sparta.springlv2project.entity.Post;
import com.sparta.springlv2project.repository.CommentRepository;
import lombok.Getter;

import java.time.LocalDateTime;

import java.util.ArrayList;

@Getter
public class PostResponseDto {
    private final Long postId;
    private final String subject;
    private final String username;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentListA = new ArrayList<>();
  
    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.subject = post.getSubject();
        this.username = post.getUsername();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        for(Comment comment : post.getCommentList()){
            commentListA.add(new CommentResponseDto(comment));
        }

    }
}

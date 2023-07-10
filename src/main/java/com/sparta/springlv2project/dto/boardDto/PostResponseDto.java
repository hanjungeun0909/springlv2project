package com.sparta.springlv2project.dto.boardDto;

import com.sparta.springlv2project.entity.Comment;
import com.sparta.springlv2project.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private final Long postId;
    private final String subject;
    private final String username;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<Comment> commentList;

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.subject = post.getSubject();
        this.username = post.getUsername();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = post.getCommentList();
    }
}

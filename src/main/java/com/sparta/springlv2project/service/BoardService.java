package com.sparta.springlv2project.service;

import com.sparta.springlv2project.dto.boardDto.CommentRequestDto;
import com.sparta.springlv2project.dto.boardDto.CommentResponseDto;
import com.sparta.springlv2project.dto.boardDto.PostResponseDto;
import com.sparta.springlv2project.dto.boardDto.PostRequestDto;
import com.sparta.springlv2project.entity.Post;
import com.sparta.springlv2project.entity.Comment;
import com.sparta.springlv2project.jwt.JwtUtil;
import com.sparta.springlv2project.repository.PostRepository;
import com.sparta.springlv2project.repository.CommentRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {


    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    public BoardService(PostRepository postRepository, CommentRepository commentRepository, JwtUtil jwtUtil) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.jwtUtil = jwtUtil;
    }

    public PostResponseDto posting(PostRequestDto postRequestDto, HttpServletRequest req) {
        Claims userInfo = getUserInfoFromRequest(req);
        Post post = new Post(postRequestDto, userInfo);
        postRepository.save(post);
        return new PostResponseDto(post);
    }


    public List<PostResponseDto> getAllPost() {

        List<Post> postList = postRepository.findByOrderByCreatedAtDesc();
        List<PostResponseDto> AllPostList = new ArrayList<>();
        for (Post post: postList) {
            AllPostList.add(new PostResponseDto(post));
        }
        return AllPostList;
    }

    public List<PostResponseDto> getUserPost(HttpServletRequest req) {
        Claims userInfo = getUserInfoFromRequest(req);
        return postRepository.findAllByUsernameOrderByCreatedAtDesc(userInfo.getSubject()).stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public PostResponseDto patchPostById(Long postId, PostRequestDto requestDto, HttpServletRequest req) {
        Claims userInfo = getUserInfoFromRequest(req);
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId 입니다."));
        if (post.verifyAuthority(userInfo, post.getUsername())) {
            post.update(requestDto);
        }
        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePostById(Long postId, HttpServletRequest req) {
        Claims userInfo = getUserInfoFromRequest(req);
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId 입니다."));
        if (post.verifyAuthority(userInfo, post.getUsername())) {
            postRepository.deleteById(postId);
        }
    }

    public CommentResponseDto commenting(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest req) {
        Claims userInfo = getUserInfoFromRequest(req);
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId 입니다."));
        Comment comment = new Comment(commentRequestDto, userInfo, post);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto patchCommentById(Long postId, Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest req) {
        Claims userInfo = getUserInfoFromRequest(req);
        postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId 입니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 commentId 입니다."));
        if (!postId.equals(comment.getPost().getPostId())) throw new IllegalArgumentException("해당 포스트의 댓글이 아닙니다.");
        if (comment.verifyAuthority(userInfo, comment.getUsername())) {
            comment.update(commentRequestDto);
        }
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteCommentById(Long postId, Long commentId, HttpServletRequest req) {
        Claims userInfo = getUserInfoFromRequest(req);
        postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId 입니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 commentId 입니다."));
        if (!postId.equals(comment.getPost().getPostId())) throw new IllegalArgumentException("해당 포스트의 댓글이 아닙니다.");

        if (comment.verifyAuthority(userInfo, comment.getUsername())) {
            commentRepository.deleteById(commentId);
        }
    }

    private Claims getUserInfoFromRequest(HttpServletRequest req) {
        String Token = jwtUtil.getTokenFromRequest(req);
        return jwtUtil.getUserInfoFromToken(jwtUtil.substringToken(Token));
    }

    public PostResponseDto getOnePostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId 입니다."));
        return new PostResponseDto(post);
    }
}

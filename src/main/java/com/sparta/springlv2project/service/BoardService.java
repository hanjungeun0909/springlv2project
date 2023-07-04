package com.sparta.springlv2project.service;

import com.sparta.springlv2project.dto.boardDto.PostResponseDto;
import com.sparta.springlv2project.dto.boardDto.PostRequestDto;
import com.sparta.springlv2project.entity.Post;
import com.sparta.springlv2project.jwt.JwtUtil;
import com.sparta.springlv2project.repository.BoardRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {


    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;

    public BoardService(BoardRepository boardRepository, JwtUtil jwtUtil) {
        this.boardRepository = boardRepository;
        this.jwtUtil = jwtUtil;
    }
    public void posting(PostRequestDto postRequestDto, HttpServletRequest res) {
        String Token = jwtUtil.getTokenFromRequest(res);
        Claims username=jwtUtil.getUserInfoFromToken(jwtUtil.substringToken(Token));
            Post post = new Post(postRequestDto, username);
            boardRepository.save(post);
    }


    public List<PostResponseDto> getAllPost() {
        return boardRepository.findAll().stream().map(PostResponseDto::new).toList();

    }
}

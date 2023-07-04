package com.sparta.springlv2project.controller;

import com.sparta.springlv2project.dto.boardDto.PostRequestDto;
import com.sparta.springlv2project.dto.boardDto.PostResponseDto;
import com.sparta.springlv2project.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardController {
    private final BoardService boardService;
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/board/posting")
    public ResponseEntity<String> posting(@RequestBody PostRequestDto postRequestDto, HttpServletRequest res){
        boardService.posting(postRequestDto, res);
        return ResponseEntity.status(HttpStatus.OK).body("포스팅 완료 !");
    }
    @GetMapping("/board/all")
    public List<PostResponseDto> getAllPost() {

        return boardService.getAllPost();
    }
}

package com.sparta.springlv2project.controller;

import com.sparta.springlv2project.dto.boardDto.CommentRequestDto;
import com.sparta.springlv2project.dto.boardDto.CommentResponseDto;
import com.sparta.springlv2project.dto.boardDto.PostRequestDto;
import com.sparta.springlv2project.dto.boardDto.PostResponseDto;
import com.sparta.springlv2project.entity.Comment;
import com.sparta.springlv2project.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/posting")
    public ResponseEntity<PostResponseDto> posting(@RequestBody PostRequestDto postRequestDto, HttpServletRequest req) {
        PostResponseDto res= boardService.posting(postRequestDto, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    @PostMapping("/{postId}/commenting")
    public ResponseEntity<CommentResponseDto> commenting(@PathVariable Long postId,
                                                         @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest req){
        CommentResponseDto res= boardService.commenting(postId, commentRequestDto, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
//    @PatchMapping("/{postId}/{commentId}")
//    public ResponseEntity<CommentResponseDto> patchPostById(@PathVariable Long postId, @PathVariable Long commentId,
//                                                             @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest req) {
//        CommentResponseDto res = boardService.patchCommentById(postId, commentId, commentRequestDto, req);
//        return ResponseEntity.status(HttpStatus.OK).body(res);
//    }
    @GetMapping("/all")
    public List<PostResponseDto> getAllPost() {
        return boardService.getAllPost();
    }

    @GetMapping("/user")
    public List<PostResponseDto> getUserPost(HttpServletRequest req) {
        return boardService.getUserPost(req);
    }

    @PatchMapping("/{postId}")
    public PostResponseDto patchPostById(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, HttpServletRequest req) {
        return boardService.patchPostById(postId, postRequestDto, req);
    }

    @DeleteMapping("/{postId}")
    public Long deletePostById(@PathVariable Long postId, HttpServletRequest req) {
        return boardService.deletePostById(postId, req);
    }
}

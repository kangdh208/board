package com.example.board.controller;

import com.example.board.dto.BoardResponseDto;
import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardSpecificResponseDto;
import com.example.board.dto.ResponseDto;
import com.example.board.security.UserDetailsImpl;
import com.example.board.service.BoardService;
import com.example.board.exception.ErrorCodes;
import com.example.board.exception.CustomException;
import com.example.board.exception.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 포스팅하기
    @PostMapping("/posts")
    public BoardResponseDto saveBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return boardService.saveBoard(boardRequestDto, userDetailsImpl.getUser());
    }

    // 모든 포스팅가져오기
    @GetMapping("/posts")
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    // 포스팅 하나만 상세조회
    @GetMapping("/posts/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    // 포스팅 수정
    @PutMapping("/posts/{id}")
    public BoardSpecificResponseDto putBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return boardService.putBoard(id, boardRequestDto, userDetailsImpl.getUser());
    }

    // 포스팅 삭제하기
    @DeleteMapping("/posts/{id}")
    public ResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return boardService.deleteBoard(id, userDetailsImpl.getUser());
    }
}
package com.example.board.controller;

import com.example.board.dto.BoardResponseDto;
import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.User;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 포스팅하기
    @PostMapping("/")
    public BoardResponseDto saveBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.saveBoard(boardRequestDto, request);
    }

    // 모든 포스팅가져오기
    @GetMapping("/")
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    // 포스팅 하나만 상세조회
    @GetMapping("/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    // 포스팅 수정
    @PutMapping("/{id}")
    public BoardResponseDto putBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.putBoard(id, boardRequestDto, request);
    }

    // 포스팅 삭제하기
    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable Long id, HttpServletRequest request){
        return boardService.deleteBoard(id, (User) request);
    }
}
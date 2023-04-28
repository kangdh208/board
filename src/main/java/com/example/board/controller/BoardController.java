package com.example.board.controller;

import com.example.board.dto.BoardResponseDto;
import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardSpecificResponseDto;
import com.example.board.dto.ResponseDto;
import com.example.board.entity.User;
import com.example.board.security.UserDetailsImpl;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public BoardResponseDto saveBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {     //객체형식으로 넘어오기 때문에 requestbody씀
        return boardService.saveBoard(boardRequestDto, userDetailsImpl.getUser());
    }

    // 모든 포스팅가져오기
    @GetMapping("/")
    public List<BoardSpecificResponseDto> getBoards() {
        return boardService.getBoards();
    }

    // 포스팅 하나만 상세조회
    @GetMapping("/{id}")
    public BoardSpecificResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    // 포스팅 수정
    @PutMapping("/{id}")
    public BoardSpecificResponseDto putBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return boardService.putBoard(id, boardRequestDto, userDetailsImpl.getUser());
    }

    // 포스팅 삭제하기
    @DeleteMapping("/{id}")
    public ResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return boardService.deleteBoard(id, userDetailsImpl.getUser());
    }
}
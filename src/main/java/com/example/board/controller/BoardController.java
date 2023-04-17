package com.example.board.controller;

import com.example.board.dto.BoardResponseDto;
import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.DeleteBoardDto;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @Transactional
    @PostMapping("/post")
    public BoardResponseDto saveBoard(@RequestBody BoardRequestDto boardRequestDto) {     //객체형식으로 넘어오기 때문에 requestbody씀
        return boardService.saveBoard(boardRequestDto);
    }

    @Transactional
    @GetMapping("/posts")
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    @Transactional
    @GetMapping("/post/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @Transactional
    @PutMapping("/post/{id}")
    public BoardResponseDto putBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.putBoard(id, boardRequestDto);
    }

    @Transactional
    @DeleteMapping("/post/{id}")
    public DeleteBoardDto deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto){
        return boardService.deleteBoard(id, boardRequestDto);
    }
}

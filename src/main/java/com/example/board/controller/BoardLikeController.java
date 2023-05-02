package com.example.board.controller;

import com.example.board.dto.ResponseDto;
import com.example.board.security.UserDetailsImpl;
import com.example.board.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardLikeController {
    private final BoardLikeService boardLikeService;

    @PostMapping("/board/like/{id}")
    public ResponseDto boardLike(@PathVariable long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return boardLikeService.boardLike(id, userDetailsImpl.getUser());
    }
}

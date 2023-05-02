package com.example.board.controller;

import com.example.board.dto.ResponseDto;
import com.example.board.security.UserDetailsImpl;
import com.example.board.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PostMapping("post/comment/like/{id}")
    public ResponseDto commentLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentLikeService.commentLike(id, userDetailsImpl.getUser());
    }
}

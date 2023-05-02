package com.example.board.service;

import com.example.board.dto.ResponseDto;
import com.example.board.entity.Board;
import com.example.board.entity.BoardLike;
import com.example.board.entity.User;
import com.example.board.exception.CustomException;
import com.example.board.exception.ErrorCode;
import com.example.board.exception.SuccessCode;
import com.example.board.repository.BoardLikeRepository;
import com.example.board.repository.BoardRepository;
import org.springframework.transaction.annotation.Transactional;

public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;

    public BoardLikeService(BoardLikeRepository boardLikeRepository, BoardRepository boardRepository) {
        this.boardLikeRepository = boardLikeRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public ResponseDto boardLike(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NO_POST_FOUND));

        if (boardLikeRepository.findByUserIdAndPostId(user.getId(), board.getId()).isEmpty()) {
            boardLikeRepository.save(new BoardLike(board, user));
            return new ResponseDto(SuccessCode.LIKE);
        } else {
            boardLikeRepository.deleteByUserIdAndPostId(user.getId(), board.getId());
            return new ResponseDto(SuccessCode.CANCEL_LIKE);
        }
    }
}

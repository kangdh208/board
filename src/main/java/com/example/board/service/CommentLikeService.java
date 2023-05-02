package com.example.board.service;

import com.example.board.dto.ResponseDto;
import com.example.board.entity.Comment;
import com.example.board.entity.CommentLike;
import com.example.board.entity.User;
import com.example.board.exception.CustomException;
import com.example.board.exception.ErrorCode;
import com.example.board.exception.SuccessCode;
import com.example.board.repository.CommentLikeRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseDto commentLike(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.NO_EXIST_COMMENT));
        if (commentLikeRepository.findByUserIdAndCommentId(user.getId(), comment.getId()).isEmpty()) {
            commentLikeRepository.save(new CommentLike(comment, user));
            return new ResponseDto(SuccessCode.LIKE);
        } else {
            commentLikeRepository.deleteByUserIdAndCommentId(user.getId(), comment.getId());
            return new ResponseDto(SuccessCode.CANCEL_LIKE);
        }
    }
}

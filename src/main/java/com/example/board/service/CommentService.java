package com.example.board.service;

import com.example.board.dto.CommentRequestDto;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.User;
import com.example.board.entity.UserRoleEnum;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성
    public String saveComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("ERROR_CODE:NO_POST_FOUND"));
        Comment comment = new Comment(commentRequestDto, board, user);
        commentRepository.save(comment);
        return "댓글저장!";
    }

    // 댓글 수정
    public String updateComment(Long id, Long commentId, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("ERROR_CODE:NO_POST_FOUND"));
        Comment comment;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("ERROR_CODE:NO_COMMENT_HERE"));
        } else {
            comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(()-> new IllegalArgumentException("ERROR_CODE:MODIFY_BLOCK_ERROR"));
        }

        comment.update(commentRequestDto);
        return "댓글 수정!";
    }

    // 댓글 삭제
    public String deleteComment(Long id, Long commentId, User user) {
        boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("ERROR_CODE:NO_POST_FOUND"));
        Comment comment;
        if(user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("ERROR_CODE:NO_COMMENT_FOUND"));
        } else {
            comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(()-> new IllegalArgumentException("ERROR_CODE:NO_COMMENT_HERE"));
        }
        commentRepository.delete(comment);
        return "댓글 삭제!";
    }
}

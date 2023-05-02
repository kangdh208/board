package com.example.board.dto;

import com.example.board.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long postId;
    private String username;
    private String comment;
    private LocalDateTime modifiedat;
    private LocalDateTime createdat;
    private int commentLikeCnt;

    //생성자
    public CommentResponseDto(Comment comment) {
        this.postId = comment.getBoard().getId();
        this.commentId = comment.getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.modifiedat = comment.getBoard().getModifiedAt();
        this.createdat = comment.getBoard().getCreatedAt();
        this.commentLikeCnt = comment.getCommentLikes().size();
    }
}

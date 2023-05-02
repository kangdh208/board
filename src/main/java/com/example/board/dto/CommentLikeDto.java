package com.example.board.dto;

import com.example.board.entity.Comment;
import com.example.board.entity.User;
import jakarta.persistence.*;

public class CommentLikeDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CommentLikeDto(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }
}

package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "Board_Id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BoardLike(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}

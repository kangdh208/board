package com.example.board.entity;
import com.example.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Board(BoardRequestDto boardRequestDto, User user){
        this.title=boardRequestDto.getTitle();
        this.content=boardRequestDto.getContent();
        this.username=user.getUsername();
        this.user = user;
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.content = boardRequestDto.getContent();
        this.title = boardRequestDto.getTitle();
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList= new ArrayList<>();
}

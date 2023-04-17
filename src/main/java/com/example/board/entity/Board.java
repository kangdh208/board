package com.example.board.entity;
import com.example.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String author;
    private String password;


    //생성자
    public Board(BoardRequestDto boardRequestDto){
        this.author=boardRequestDto.getAuthor();
        this.title=boardRequestDto.getTitle();
        this.content=boardRequestDto.getContent();
        this.password=boardRequestDto.getPassword();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.author = boardRequestDto.getAuthor();
        this.content = boardRequestDto.getContent();
        this.title = boardRequestDto.getTitle();
    }
}

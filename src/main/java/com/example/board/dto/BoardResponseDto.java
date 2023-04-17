package com.example.board.dto;

import com.example.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdat;
    private LocalDateTime modifiedat;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
        this.createdat = board.getCreatedAt();
        this.modifiedat = board.getModifiedAt();

    }
}

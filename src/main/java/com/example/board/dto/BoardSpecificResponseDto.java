package com.example.board.dto;

import com.example.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardSpecificResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdat;
    private LocalDateTime modifiedat;
    private String username;
    private int postLikeCnt;
    List<CommentResponseDto> commentList;

    //생성자
    public BoardSpecificResponseDto(Board board, List<CommentResponseDto> commentList){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdat = board.getCreatedAt();
        this.modifiedat = board.getModifiedAt();
        this.username = board.getUsername();
        this.commentList = commentList;
    }
}

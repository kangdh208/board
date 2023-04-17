package com.example.board.dto;

import com.example.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteBoardDto {
    private String msg;

    //메서드
    public void setMsg(String msg) {
        this.msg = msg;
    }
}

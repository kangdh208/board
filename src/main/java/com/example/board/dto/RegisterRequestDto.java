package com.example.board.dto;

import lombok.Getter;

@Getter
public class RegisterRequestDto {
    private final String userid;
    private final String password;
    private final String username;
    private final String email;

    public RegisterRequestDto(String userid, String password, String username, String email) {
        this.userid = userid;
        this.password = password;
        this.username = username;
        this.email = email;
    }
}

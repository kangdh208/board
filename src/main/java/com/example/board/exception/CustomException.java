package com.example.board.exception;

import lombok.Getter;
import com.example.board.exception.ErrorCodes;


@Getter
public class CustomException extends RuntimeException{
    private int statuscode;
    private String msg;

    public CustomException(ErrorCodes errorCode) {
        this.statuscode = errorCode.getHttpStatus().value();
        this.msg = errorCode.getMessage();
    }
}
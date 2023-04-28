package com.example.board.dto;


import com.example.board.exception.CustomException;
import com.example.board.exception.SuccessCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@Setter
public class ResponseDto {
    private String msg;
    private int statusCode;

    public ResponseDto(SuccessCode successCode) {
        this.msg = successCode.getMessage();
        this.statusCode = successCode.getHttpStatus().value();
    }

    public ResponseDto(CustomException customException) {
        this.msg = customException.getMsg();
        this.statusCode = customException.getStatuscode();
    }

    public ResponseDto(MethodArgumentNotValidException ex) {
        this.msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        this.statusCode = HttpStatus.BAD_REQUEST.value();
    }
}

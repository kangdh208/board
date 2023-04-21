package com.example.board.controller;

import com.example.board.dto.LoginRequestDto;
import com.example.board.dto.SignupRequestDto;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입 기능 (ADMIN_TOKEN 넣을시 ADMIN 권한 가짐. ADMIN_TOKEN 안 넣으면 일반 USER 권한)
    @PostMapping("signup")
    public String signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    // 로그인 기능
    @PostMapping("login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto, response);
    }
}

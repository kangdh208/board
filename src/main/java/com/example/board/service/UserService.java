package com.example.board.service;

import com.example.board.dto.LoginRequestDto;
import com.example.board.dto.SignupRequestDto;
import com.example.board.entity.Users;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //회원가입 기능
    @Transactional
    public String signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // 회원 중복 확인
        Optional<Users> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("ErrorCodes.ALREADY_EXIST_USERNAME");
        }
        Users user = new Users(username, password);
        userRepository.save(user);
        return "SuccessCode.SIGN_UP";
    }

    //로그인기능
    @Transactional(readOnly = true)
    public String login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("ErrorCodes.NO_EXIST_USER"));

        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
        return "LOGIN!";
    }
}
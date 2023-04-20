package com.example.board.service;

import com.example.board.dto.LoginRequestDto;
import com.example.board.dto.ResponseDto;
import com.example.board.dto.SignupRequestDto;
import com.example.board.entity.Users;
import com.example.board.entity.UserRoleEnum;
import com.example.board.exception.CustomException;
import com.example.board.exception.ErrorCodes;
import com.example.board.exception.SuccessCode;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입 기능
    @Transactional
    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        String name = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<Users> found = userRepository.findByUsername(name);
        if (found.isPresent()) {
            throw new CustomException(ErrorCodes.ALREADY_EXIST_USERNAME);
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomException(ErrorCodes.DISMATCH_ADMIN_TOKEN);
            }
            role = UserRoleEnum.ADMIN;
        }

        Users user = new Users(name, password, role);
        userRepository.save(user);
        return new ResponseDto(SuccessCode.SIGN_UP);
    }

    //로그인기능
    @Transactional(readOnly = true)
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCodes.NO_EXIST_USER));

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){throw new CustomException(ErrorCodes.DISMATCH_PASSWORD);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new ResponseDto(SuccessCode.LOG_IN);
    }
}
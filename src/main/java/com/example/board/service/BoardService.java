package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.entity.Board;
import com.example.board.entity.UserRoleEnum;
import com.example.board.entity.User;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    // 글 등록
    public BoardResponseDto saveBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = new Board(boardRequestDto, user);
            boardRepository.save(board);
            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }
    // 글 목록 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards() {
        return boardRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(BoardResponseDto::new).collect(Collectors.toList());
    }
    // 글 조회
    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new BoardResponseDto(board);
    }
    // 글 수정
    @Transactional
    public BoardResponseDto putBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            if(user.getUsername().equals(board.getUsername())) {
                board.update(boardRequestDto);
                return new BoardResponseDto(board);
            } else {
                throw new IllegalArgumentException("작성자만 수정, 삭제 가능합니다.");
            }
        } else {
            throw new NoSuchElementException("올바르지 않은 접근입니다.");
        }
    }
    // 글 삭제
    @Transactional
    public String deleteBoard(Long id, User user) {
        Board board;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ERROR_CODES:NO_POST_FOUND"));
            boardRepository.delete(board);
        } else {
            board = boardRepository.findById(id).orElseThrow(()->new IllegalArgumentException("ERROR_CODE:NO_DELETE_POST"));
            boardRepository.delete(board);
        }

        return "게시물 삭제";
    }
}
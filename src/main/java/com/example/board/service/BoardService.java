package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.dto.BoardSpecificResponseDto;
import com.example.board.dto.ResponseDto;
import com.example.board.entity.Board;
import com.example.board.entity.UserRoleEnum;
import com.example.board.entity.Users;
import com.example.board.exception.CustomException;
import com.example.board.exception.ErrorCodes;
import com.example.board.exception.SuccessCode;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final Users users;

    @Transactional
    public BoardResponseDto saveBoard(BoardRequestDto boardRequestDto, Users user) {
        Board board = new Board(boardRequestDto, user);
        boardRepository.save(board);
        return new BoardResponseDto(board);


    }

    public List<BoardResponseDto> getBoards() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board board : boardList) {
            BoardResponseDto boardResponseDto1 = new BoardResponseDto(board);
            boardResponseDto.add(boardResponseDto1);
        }
        return boardResponseDto;
    }

    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("존재하지 않는 글입니다")
        );
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardSpecificResponseDto putBoard(Long id, BoardRequestDto boardRequestDto, Users user) {
        Board board;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            board = boardRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCodes.ALREADY_EXIST_USERNAME));
            board.update(boardRequestDto);
        } else {
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(()->new CustomException(ErrorCodes.NO_POST_FOUND));
            board.update(boardRequestDto);
        }
        return new BoardSpecificResponseDto(board);
    }

    @Transactional
    public ResponseDto deleteBoard(Long id, Users user) {
        Board board;
        board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException(" ")
        );
        boardRepository.delete(board);
//        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
//            board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCodes.NO_POST_FOUND));
//            boardRepository.delete(board);
//        } else {
//            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(() -> new CustomException(ErrorCodes.NO_DELETE_POST));
//            boardRepository.delete(board);
//        }
        return new ResponseDto(SuccessCode.DELETE_POST);
    }
}
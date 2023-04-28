package com.example.board.service;

import com.example.board.dto.*;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.UserRoleEnum;
import com.example.board.entity.User;
import com.example.board.exception.CustomException;
import com.example.board.exception.ErrorCode;
import com.example.board.exception.SuccessCode;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final User user;
    // 글 등록
    public BoardResponseDto saveBoard(BoardRequestDto boardRequestDto, User user) {
        Board board = boardRepository.save(new Board(boardRequestDto, user));        //데이터베이스에 연결해서 저장하려면 @Entity어노테이션이 걸려져있는 Post클래스를 인스턴스로 만들어서 그 값을사용해서 저장해야 함. 그렇기때문에 board 객체를 만들어주고 생성자를 사용해서 값들을 넣어줘야 함.
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return boardResponseDto;
    }


    // 글 목록 조회
    public List<BoardSpecificResponseDto> getBoards() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardSpecificResponseDto> postSpecificResponseDtoList = new ArrayList<>();

        for (Board board: boardList) {
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : board.getCommentList()) {
                commentList.add(new CommentResponseDto(comment));
            }
            BoardSpecificResponseDto boardSpecificResponseDto = new BoardSpecificResponseDto(board, commentList);
            postSpecificResponseDtoList.add(boardSpecificResponseDto);
        }
        return postSpecificResponseDtoList;
    }
    // 글 조회
    public BoardSpecificResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NO_POST_FOUND));
        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : board.getCommentList()) {
            commentList.add(new CommentResponseDto(comment));
        }
        return new BoardSpecificResponseDto(board, commentList);
    }
    // 글 수정
    @Transactional
    public BoardSpecificResponseDto putBoard(Long id, BoardRequestDto boardRequestDto, User user) {
        Board board;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            // 관리자 권한이 ADMIN인 경우
            board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ALREADY_EXIST_USERNAME));
            board.update(boardRequestDto);
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : board.getCommentList()) {
                commentList.add(new CommentResponseDto(comment));
            }
            return new BoardSpecificResponseDto(board, commentList);
        } else {
            // 사용자 권한이 USER일 경우
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(() -> new CustomException(ErrorCode.NO_POST_FOUND));
            board.update(boardRequestDto);
            List<CommentResponseDto> commentList = new ArrayList<>();             //이부분 수정필요! 이진님께서 짧게 줄일수이다고 알려주심.
            for(Comment comment : board.getCommentList()){
                commentList.add(new CommentResponseDto(comment));
            }
            return new BoardSpecificResponseDto(board, commentList);
        }
    }
    // 글 삭제
    @Transactional
    public ResponseDto deleteBoard(Long id, User user) {
        Board board;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            // ADMIN 권한일 때
            board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NO_POST_FOUND));
            boardRepository.delete(board);
        } else {
            // USER 권할일 때
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(() -> new CustomException(ErrorCode.NO_DELETE_POST));
            boardRepository.delete(board);
        }
        return new ResponseDto(SuccessCode.DELETE_POST);
    }
}
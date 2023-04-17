package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.dto.DeleteBoardDto;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;                 // 싱글톤이어야하기 때문!

    @Transactional
    public BoardResponseDto saveBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);       //데이터베이스에 연결해서 저장하려면 @Entity어노테이션이 걸려져있는 Board클래스를 인스턴스로 만들어서 그 값을사용해서 저장해야 함. 그렇기때문에 board 객체를 만들어주고 생성자를 사용해서 값들을 넣어줘야 함.
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
    public BoardResponseDto putBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("존재하지 않는 글입니다")
        );
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

        if (boardRequestDto.getPassword().equals(board.getPassword())) {
            board.update(boardRequestDto);
            return boardResponseDto;
        } else {
            return boardResponseDto;
        }
    }

    public DeleteBoardDto deleteBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("존재하지 않는 글입니다")
        );
        DeleteBoardDto deleteBoardDto = new DeleteBoardDto();
        if (boardRequestDto.getPassword().equals(board.getPassword())) {
            boardRepository.deleteById(id);
            deleteBoardDto.setMsg("success");
        } else {
            deleteBoardDto.setMsg("fail");
        }
        return deleteBoardDto;
    }
}